package src.mpp2024.objectProtocol;

import src.mpp2024.domain.*;
import src.mpp2024.dto.ListInscrieriProbaDTO;
import src.mpp2024.dto.PersoanaOficiuDTO;
import src.mpp2024.services.ConcursException;
import src.mpp2024.services.IConcursService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class ConcursServicesObjectProxy implements IConcursService {

    private String host;
    private int port;

    public ConcursServicesObjectProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private Object sendRequest(Object request) throws Exception {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(request);
            out.flush();

            return in.readObject();
        }
    }

    @Override
    public List<Inscriere> getInscrieriProba(int probaId) throws Exception {
        Object response = sendRequest("GET_INSCRIERI_PROBA:" + probaId);
        if (response instanceof ListInscrieriProbaDTO dto) {
            return dto.getInscrieriProbe();
        }
        throw new RuntimeException("Invalid response from server.");
    }

    @Override
    public Map<String, Map<String, Integer>> getCompetitionsWithParticipants() {
        return Map.of();
    }

    @Override
    public NumeProba getNumeProba(String selectedProba) {
        return null;
    }

    @Override
    public CategorieVarsta getCategorie(int i) {
        return null;
    }

    @Override
    public List<Participant> getParticipantsByProbaAndCategorie(NumeProba proba, CategorieVarsta categorie) {
        return List.of();
    }

    @Override
    public CategorieVarsta getCategorieVarstaByAge(int varsta) {
        return null;
    }

    @Override
    public Participant getParticipantByCNP(String cnp) {
        return null;
    }

    @Override
    public boolean saveEntity(Participant p) {
        return false;
    }

    @Override
    public boolean saveEntityi(Inscriere i) {
        return false;
    }

    @Override
    public PersoanaOficiu login(String username, String password) throws Exception {
        Object response = sendRequest("LOGIN:" + username + "," + password);
        if (response instanceof PersoanaOficiuDTO dto) {
            return dto.getPersoanaOficiu();
        }
        throw new RuntimeException("Login failed.");
    }

    private void initializeConnection() throws ConcursException {
        try{
            connection=new Socket(host,port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        }catch(IOException e){
            logger.error(e.getMessage());
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
}
