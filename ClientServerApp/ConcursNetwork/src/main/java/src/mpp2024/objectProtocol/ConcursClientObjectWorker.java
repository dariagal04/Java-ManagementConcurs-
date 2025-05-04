package src.mpp2024.objectProtocol;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.mpp2024.domain.Inscriere;
import src.mpp2024.domain.PersoanaOficiu;
import src.mpp2024.dto.ListInscrieriProbaDTO;
import src.mpp2024.dto.PersoanaOficiuDTO;
import src.mpp2024.services.IConcursService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.stream.StreamSupport;

public class ConcursClientObjectWorker {

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
                Object request=input.readObject();
                Object response=handleRequest((Request)request);
                if(response != null){
                    sendResponse((Response)response);
                }
            }catch(IOException|ClassNotFoundException e){
                logger.error("Eroareeee*: "+e);
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

    private Response handleRequest(Request request) {
        Response response=null;
        if(request instanceof LogInRequest loginRequest){
            logger.debug("Login request...");
            PersoanaOficiuDTO persoanaOficiuDTO=loginRequest.getPersoanaOficiuDTO();
            PersoanaOficiu persoanaOficiu = getFromDTO(refereeDTO);
            try{
                server.logIn(referee,this);
                return new OkResponse();
            } catch (TriathlonException e) {
                connected=false;
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof LogOutRequest logoutRequest){
            logger.debug("Logout request...");
            RefereeDTO refereeDTO=logoutRequest.getReferee();
            Referee referee= DTOUtils.getFromDTO(refereeDTO);
            try{
                server.logOut(referee,this);
                connected=false;
                return new OkResponse();
            }catch(TriathlonException e){
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof AddResultRequest addResultRequest){
            logger.debug("Add result request...");
            ResultDTO resultDTO=addResultRequest.getResult();
            Result result= DTOUtils.getFromDTO(resultDTO);
            try{
                server.addResult(result);
                return new OkResponse();
            }catch(TriathlonException e){
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof FindAllParticipantsRequest) {
            logger.debug("Handling FindAllParticipantsSortedByNameRequest...");
            try {
                Iterable<Participant> participants = server.findAllParticipantsSortedByName();
                List<ParticipantDTO> dtos = StreamSupport.stream(participants.spliterator(), false)
                        .map(DTOUtils::getDTO)
                        .toList();
                return new FindAllParticipantsResponse(dtos);
            } catch (TriathlonException e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof GetParticipantsAtEventRequest) {
            logger.debug("Handling GetAllParticipantsAtEventRequest...");
            try {
                Referee referee=DTOUtils.getFromDTO(((GetParticipantsAtEventRequest) request).getReferee());
                List<Result> results = server.getParticipantsSortedByPoints(referee);
                List<ResultDTO> dtos = StreamSupport.stream(results.spliterator(), false)
                        .map(DTOUtils::getDTO)
                        .toList();
                return new GetParticipantsAtEventResponse(dtos);
            } catch (TriathlonException e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof FindRefereeByUsernamePasswordRequest){
            logger.debug("Handling FindRefereeByUsernamePasswordRequest...");
            try{
                RefereeDTO dto=((FindRefereeByUsernamePasswordRequest) request).getUsername_password();
                Referee referee=server.getRefereeByUsernamePassword(dto.getUsername(), dto.getPassword());
                RefereeDTO newDto=DTOUtils.getDTO(referee);
                return new FindRefereeByUsernamePasswordResponse(newDto);
            }catch (TriathlonException e){
                connected=false;
                return new ErrorResponse(e.getMessage());
            }
        }
        return response;
    }

    @Override
    public void resultAdded(Result result) throws TriathlonException {
        ResultDTO resultDTO=DTOUtils.getDTO(result);
        logger.debug(resultDTO.toString());
        try{
            sendResponse(new NewResultResponse(resultDTO));
        }catch(IOException e){
            logger.error(e.getMessage());
        }
    }

    @Override
    public void refereeLoggedIn(Referee referee) throws TriathlonException {
        RefereeDTO refereeDTO=DTOUtils.getDTO(referee);
        logger.debug(refereeDTO.toString());
        try{
            sendResponse(new RefereeLoggedInResponse(refereeDTO));
        }catch(IOException e){
            logger.error(e.getMessage());
        }
    }

    @Override
    public void refereeLoggedOut(Referee referee) throws TriathlonException {
        RefereeDTO refereeDTO=DTOUtils.getDTO(referee);
        logger.debug(refereeDTO.toString());
        try{
            sendResponse(new RefereeLogedOutResponse(refereeDTO));
        }catch (IOException e){
            logger.error(e.getMessage());
        }
    }


    private void sendResponse(Response response) throws IOException {
        logger.debug(response.toString());
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }
}
