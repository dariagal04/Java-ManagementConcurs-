package src.mpp2024.objectProtocol;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.mpp2024.domain.CategorieVarsta;
import src.mpp2024.domain.NumeProba;
import src.mpp2024.domain.Participant;
import src.mpp2024.domain.PersoanaOficiu;
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
import java.util.stream.StreamSupport;

import static src.mpp2024.dto.DTOUtils.getFromDTO;

public class ConcursClientObjectWorker implements Runnable, IConcursObserver {

    private final IConcursService server;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    private static final Logger logger= LogManager.getLogger(ConcursClientObjectWorker.class);


    private Socket connection;

    public ConcursClientObjectWorker(IConcursService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        }catch(IOException e){
            logger.error(e.getMessage());
        }
    }

    @Override
    public void run() {
        while(connected){
            try{
                logger.info("astept request de la client");
                Object request=input.readObject();
                logger.info("Primit request"+request);
                Object response=handleRequest((Request)request);
                if(response != null){
                    sendResponse((Response)response);
                }
            }catch(IOException|ClassNotFoundException|ConcursException e){
                logger.error("Eroare: "+e);
            }
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                logger.error(e.getMessage());
            }
        }

        try{
            input.close();
            output.close();
            connection.close();
        }catch(IOException e){
            logger.error(e.getMessage());
        }
    }

    private void sendResponse(Response response) throws IOException {
        logger.debug(response.toString());
        synchronized (output){
            output.writeObject(response);
            output.flush();
        }
    }

    private Response handleRequest(Request request) throws ConcursException {

        Response response=null;
        if(request instanceof LogInRequest loginRequest){
            logger.debug("Login request...");
            System.out.println(" Server received login request: " + loginRequest.getPersoanaOficiuDTO().getUsername());

            PersoanaOficiuDTO persoanaOficiuDTO=loginRequest.getPersoanaOficiuDTO();
            PersoanaOficiu persoanaOficiu = DTOUtils.getFromDto(persoanaOficiuDTO);
            try{
                server.login(persoanaOficiu,this);

                return new OkResponse();
            } catch (Exception e) {
                connected=false;
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof LogOutRequest logoutRequest){
            logger.debug("Logout request...");
            PersoanaOficiuDTO persoanaOficiuDTO=logoutRequest.getPersoanaOficiuDTO();
            PersoanaOficiu persoanaOficiu= DTOUtils.getFromDto(persoanaOficiuDTO);
            try{
                server.logout(persoanaOficiu,this);
                connected=false;
                return new OkResponse();
            }catch(Exception e){
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof FindPOByUsernamePasswordRequest gpRequest) {
            logger.debug("Handling GetPersoanaOficiuByUsernamePasswordRequest");

            String username = gpRequest.getPersoanaOficiu().getUsername();
            String password = gpRequest.getPersoanaOficiu().getPassword();
            logger.info("Looking for user: " + username);

            PersoanaOficiu user = server.getPersoanaOficiuByUsernamePassword(username, password);
            if (user == null) {
                logger.warn("User not found for username: " + username);
                return new ErrorResponse("Utilizator inexistent");
            }

            PersoanaOficiuDTO dto = DTOUtils.getDTO(user);
            return new FindPOByUsernamePasswordResponse(dto);
        }
        if (request instanceof GetParticipantsByProbaAndCategorieRequest req) {
            logger.debug("Processing GetParticipantsByProbaAndCategorieRequest...");

            NumeProba proba = req.getProba();
            CategorieVarsta categorie = req.getCategorie();

            try {
                List<Participant> participants = server.getParticipantsByProbaAndCategorie(proba, categorie);
                return new GetParticipantsByProbaAndCategorieResponse(participants);
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage());
            }
        }


        if (request instanceof NewParticipantResponse req) {
            try {
                Participant participantDTO = req.getParticipantDTO();
                //Participant participant = DTOUtils.getFromDTO(participantDTO);
                boolean saved = server.saveEntity(participantDTO);

                if (saved)
                    return new OkResponse();
                else
                    return new ErrorResponse("Participant could not be saved.");
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof NewInscriereResponse req) {
            try {
                boolean saved = server.saveEntityi(req.getInscriere());

                if (saved)
                    return new OkResponse();
                else
                    return new ErrorResponse("Inscriere could not be saved.");
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof GetCompetitionsWithParticipantsRequest) {
            logger.debug("Handling GetCompetitionsWithParticipantsRequest...");
            try {
                Map<String, Map<String, Integer>> data = server.getCompetitionsWithParticipants();
                return new GetCompetitionsWithParticipantsResponse(data);
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof SaveParticipantRequest saveRequest) {
            try {
                boolean saved = server.saveEntity(saveRequest.getParticipant());
                return saved ? new OkResponse() : new ErrorResponse("Participant could not be saved.");
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof SaveInscriereRequest saveRequest) {
            try {
                boolean saved = server.saveEntityi(saveRequest.getInscriere());
                return saved ? new OkResponse() : new ErrorResponse("Inscriere could not be saved.");
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof GetNumeProbaRequest req) {
            try {
                NumeProba proba = server.getNumeProba(req.getNumeProba());
                return new GetNumeProbaResponse(proba);
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof GetCategorieRequest req) {
            try {
                CategorieVarsta categorie = server.getCategorie(req.getId());
                return new GetCategorieResponse(categorie);
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof GetCategorieByAgeRequest req) {
            try {
                CategorieVarsta categorie = server.getCategorieVarstaByAge(req.getAge());
                return new GetCategorieByAgeResponse(categorie);
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof GetParticipantByCNPRequest req) {
            try {
                Participant p = server.getParticipantByCNP(req.getCNP());
                return new GetParticipantByCNPResponse(p);
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage());
            }
        }













        return response;
    }



    @Override
    public void participantAdded(Participant participant) throws ConcursException {
        ParticipantDTO participantDTO= DTOUtils.getDTO(participant);
        Participant participant1 = DTOUtils.getFromDTO(participantDTO);
        logger.debug(participantDTO.toString());
        try{
            sendResponse(new NewParticipantResponse(participant1));
        }
        catch(IOException e){
            logger.error(e.getMessage());
        }
    }

    @Override
    public void oficiuLoggedIn(PersoanaOficiu persoanaOficiu) throws ConcursException {

        PersoanaOficiuDTO persoanaOficiuDTO= DTOUtils.getDTO(persoanaOficiu);
        logger.debug(persoanaOficiuDTO.toString());
        try{
            sendResponse(new POLoggedInResponse(persoanaOficiuDTO));
        }
        catch(IOException e){
            logger.error(e.getMessage());
        }
    }

    @Override
    public void oficiuLoggedOut(PersoanaOficiu persoanaOficiu) throws ConcursException {

        PersoanaOficiuDTO persoanaOficiuDTO= DTOUtils.getDTO(persoanaOficiu);
        logger.debug(persoanaOficiuDTO.toString());
        try{
            sendResponse(new POLoggedOutResponse(persoanaOficiuDTO));
        }
        catch(IOException e){
            logger.error(e.getMessage());
        }
    }
}
