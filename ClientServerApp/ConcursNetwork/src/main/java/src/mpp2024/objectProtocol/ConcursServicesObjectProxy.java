package src.mpp2024.objectProtocol;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.mpp2024.domain.*;
import src.mpp2024.dto.DTOUtils;
import src.mpp2024.dto.ParticipantDTO;
import src.mpp2024.dto.PersoanaOficiuDTO;
import src.mpp2024.services.ConcursException;
import src.mpp2024.services.IConcursObserver;
import src.mpp2024.services.IConcursService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConcursServicesObjectProxy implements IConcursService {

    private String host;
    private int port;

    private IConcursObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private static final Logger logger= LogManager.getLogger(ConcursServicesObjectProxy.class);

    private final BlockingQueue<Response> responseQueue;
    private volatile boolean finished;

    public ConcursServicesObjectProxy(String host, int port) {
        this.host = host;
        this.port = port;

        responseQueue= new LinkedBlockingQueue<Response>();
    }


    private Response readResponse() throws ConcursException {
        Response response=null;
        try{
            response=responseQueue.take();
        }catch(InterruptedException e){
            logger.error(e.getMessage());
        }
        return response;
    }
    private void sendRequest(Request request) throws ConcursException {
        try{
            output.writeObject(request);
            output.flush();
        }catch(IOException e){
            throw new ConcursException(e.getMessage());
        }
    }
    private void handleUpdate(UpdateResponse update){
        if(update instanceof POLoggedInResponse poLoggedInResponse){
            PersoanaOficiu persoanaOficiu= DTOUtils.getFromDto(poLoggedInResponse.getPersoanaOficiu());
            logger.debug("Referee logged in "+persoanaOficiu);
            try{
                client.oficiuLoggedIn(persoanaOficiu);
            }catch(ConcursException e){
                logger.error(e.getMessage());
            }
        }
        if(update instanceof POLoggedOutResponse poLoggedOutResponse){
            PersoanaOficiu persoanaOficiu= DTOUtils.getFromDto(poLoggedOutResponse.getPersoanaOficiu());
            logger.debug("Referee logged in "+persoanaOficiu);
            try{
                client.oficiuLoggedOut(persoanaOficiu);
            }catch(ConcursException e){
                logger.error(e.getMessage());
            }
        }
        if(update instanceof NewParticipantResponse newParticipantResponse){
            //Participant participant= DTOUtils.getFromDTO(newParticipantResponse.getParticipantDTO());
            Participant participant = newParticipantResponse.getParticipantDTO();
            try{
                client.participantAdded(participant);
            }catch(ConcursException e){
                logger.error(e.getMessage());
            }
        }
        if(update instanceof  NewParticipantResponse newParticipantResponse){
            Participant participant = newParticipantResponse.getParticipantDTO();
            //Participant participant1=DTOUtils.getFromDTO(newParticipantResponse.getParticipantDTO());
            try{
                client.participantAdded(participant);
            }catch(ConcursException e){
                logger.error(e.getMessage());
            }
        }
    }

    private class ReaderThread implements Runnable{
        public void run(){
            while(!finished){
                try{
                    logger.info("Waiting for response...");
                    Object response=input.readObject();
                    logger.info("Response: "+response);
                    logger.debug("Response received: " + response.getClass().getName());
                    if(response instanceof UpdateResponse){
                        handleUpdate((UpdateResponse) response);
                    }else{
                        try{
                            responseQueue.put((Response) response);
                        }catch(InterruptedException e){
                            logger.error(e.getMessage());
                        }
                    }
                }catch(IOException|ClassNotFoundException e){
                    if (!finished) {
                        logger.error("Reading error: " + e);
                    } else {
                        logger.debug("ReaderThread stopped (socket closed).");
                    }
                }
            }
        }
    }

    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }
    private void initializeConnection() throws ConcursException {
        logger.info("initializing connection to host: " + host+ " and port:" + port);
        try{
            connection=new Socket(host,port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        }catch(IOException e){

            logger.error(e.getMessage());
            throw new ConcursException("Could not connect to host: " + host + " and port: " + port);
        }
    }

    private void closeConnection(){
        finished=true;
        try{
            input.close();
            output.close();
            connection.close();
            client=null;
        }catch(IOException e){
            logger.error(e.getMessage());
        }
    }


    @Override
    public PersoanaOficiu getPersoanaOficiuByUsernamePassword(String username, String password) throws ConcursException {
        initializeConnection();
        PersoanaOficiuDTO dto=new PersoanaOficiuDTO(username,password);
        sendRequest(new FindPOByUsernamePasswordRequest(dto));

        Response response=readResponse();
        if(response instanceof FindPOByUsernamePasswordResponse resp){
            return DTOUtils.getFromDto(resp.getPersoanaOficiu());
        }
        if(response instanceof ErrorResponse errorResponse){
            closeConnection();
            throw new ConcursException(errorResponse.getMessage());
        }
        return null;
    }
//
//    @Override
//    public Map<String, Map<String, Integer>> getCompetitionsWithParticipants() throws ConcursException {
//        return Map.of();
//    }
//
//    @Override
//    public List<Participant> getParticipantsByProbaAndCategorie(NumeProba proba, CategorieVarsta categorie) throws ConcursException {
//        return List.of();
//    }


    public void login(PersoanaOficiu persoanaOficiu, IConcursObserver client) throws ConcursException {
        initializeConnection();
        this.client=client;
        PersoanaOficiuDTO persoanaOficiuDTO=DTOUtils.getDTO(persoanaOficiu);
        logger.info("Trimit login request cu: "+ persoanaOficiuDTO);
        sendRequest(new LogInRequest(persoanaOficiuDTO));
        logger.info("Login request trimis");

        Response response=readResponse();
        if(response instanceof OkResponse){
            //this.client=client;
            return;
        }
        if(response instanceof ErrorResponse errorResponse){
            closeConnection();
            throw new ConcursException(errorResponse.getMessage());
        }
    }
    public void logout(PersoanaOficiu persoanaOficiu, IConcursObserver client) throws ConcursException {

        PersoanaOficiuDTO persoanaOficiuDTO=DTOUtils.getDTO(persoanaOficiu);
        sendRequest(new LogOutRequest(persoanaOficiuDTO));
        Response response=readResponse();
        closeConnection();
        if(response instanceof ErrorResponse errorResponse){
            throw new ConcursException(errorResponse.getMessage());
        }
    }

    @Override
    public Map<String, Map<String, Integer>> getCompetitionsWithParticipants() throws ConcursException {
        sendRequest(new GetCompetitionsWithParticipantsRequest());
        Response response = readResponse();

        if (response instanceof GetCompetitionsWithParticipantsResponse resp) {
            return resp.getData();
        }

        if (response instanceof ErrorResponse errorResponse) {
            throw new ConcursException(errorResponse.getMessage());
        }

        return Map.of();
    }

    @Override
    public NumeProba getNumeProba(String selectedProba) throws ConcursException {

        sendRequest(new GetNumeProbaRequest(selectedProba));
        Response response = readResponse();

        if (response instanceof GetNumeProbaResponse resp) {
            return resp.getNumeProba();
        }
        if (response instanceof ErrorResponse errorResponse) {
            throw new ConcursException(errorResponse.getMessage());
        }
        return null;
    }



    @Override
    public CategorieVarsta getCategorie(int id) throws ConcursException {
        sendRequest(new GetCategorieRequest(id));
        Response response = readResponse();

        if (response instanceof GetCategorieResponse resp) {
            return resp.getCategorie();
        }
        if (response instanceof ErrorResponse errorResponse) {
            throw new ConcursException(errorResponse.getMessage());
        }
        return null;
    }


    @Override
    public List<Participant> getParticipantsByProbaAndCategorie(NumeProba proba, CategorieVarsta categorie) throws ConcursException {
        sendRequest(new GetParticipantsByProbaAndCategorieRequest(proba, categorie));
        Response response = readResponse();

        if (response instanceof GetParticipantsByProbaAndCategorieResponse resp) {
            return resp.getParticipants(); // presupunem că e List<Participant>
        }

        if (response instanceof ErrorResponse errorResponse) {
            throw new ConcursException(errorResponse.getMessage());
        }

        return List.of();
    }

    @Override
    public CategorieVarsta getCategorieVarstaByAge(int varsta) throws ConcursException {
        sendRequest(new GetCategorieByAgeRequest(varsta));
        Response response = readResponse();

        if (response instanceof GetCategorieByAgeResponse resp) {
            return resp.GetCategorieVarsta();
        }
        if (response instanceof ErrorResponse errorResponse) {
            throw new ConcursException(errorResponse.getMessage());
        }
        return null;
    }


    @Override
    public Participant getParticipantByCNP(String cnp) throws ConcursException {
        sendRequest(new GetParticipantByCNPRequest(cnp));
        Response response = readResponse();

        if (response instanceof GetParticipantByCNPResponse resp) {
            return resp.getParticipant();
        }
        if (response instanceof ErrorResponse errorResponse) {
            throw new ConcursException(errorResponse.getMessage());
        }
        return null;
    }


    @Override
    public boolean saveEntity(Participant p) throws ConcursException {
        sendRequest(new SaveParticipantRequest(p));
        Response response = readResponse();

        if (response instanceof OkResponse) {
            return true;
        }
        if (response instanceof ErrorResponse errorResponse) {
            throw new ConcursException(errorResponse.getMessage());
        }
        return false;
    }


    @Override
    public boolean saveEntityi(Inscriere i) throws ConcursException {
        sendRequest(new SaveInscriereRequest(i));
        Response response = readResponse();

        if (response instanceof OkResponse) {
            return true;
        }
        if (response instanceof ErrorResponse errorResponse) {
            throw new ConcursException(errorResponse.getMessage());
        }
        return false;
    }


}
