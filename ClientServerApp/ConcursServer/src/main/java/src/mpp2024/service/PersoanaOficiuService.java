package src.mpp2024.service;

import src.mpp2024.domain.*;
import src.mpp2024.dto.PersoanaOficiuDTO;
import src.mpp2024.repo.DB.PersoanaOficiuDBRepo;
import src.mpp2024.repo.Interfete.IPersoanaOficiuRepo;
import src.mpp2024.services.ConcursException;
import src.mpp2024.services.IConcursObserver;
import src.mpp2024.services.IConcursService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PersoanaOficiuService implements IConcursService {

    private final IPersoanaOficiuRepo persoanaOficiuDBRepo;
    private Map<Integer,IConcursObserver> loggedPOs;


    public PersoanaOficiuService(IPersoanaOficiuRepo persoanaOficiuDBRepo) {
        this.persoanaOficiuDBRepo = persoanaOficiuDBRepo;
        loggedPOs = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(PersoanaOficiu persoanaOficiu, IConcursObserver client) throws ConcursException {
        PersoanaOficiu persoanaOficiu1= persoanaOficiuDBRepo.getOneByUsername(persoanaOficiu.getUsername());
        if (persoanaOficiu1 != null) {
            if(loggedPOs.get(persoanaOficiu1.getId()) != null)
                throw new ConcursException("PersoanaOficiu already logged");
            loggedPOs.put(persoanaOficiu1.getId(), client);
        }else
            throw new ConcursException("PersoanaOficiu not found");

    }

    @Override
    public PersoanaOficiu getPersoanaOficiuByUsernamePassword(String username, String password) throws ConcursException {
        PersoanaOficiu persoanaOficiu= persoanaOficiuDBRepo.getOneByUsername(username);
        if (persoanaOficiu != null && persoanaOficiu.getPassword().equals(password)) {
            return persoanaOficiu;
        }
        return null;
    }

    @Override
    public void logout(PersoanaOficiu persoanaOficiu, IConcursObserver client) throws ConcursException {
        IConcursObserver localClient = loggedPOs.remove(persoanaOficiu.getId());
        if(localClient == null)
            throw new ConcursException("Persoana Oficiu "+persoanaOficiu.getId()+" is not logged in.");
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

//    public void authenticate(String username, String password) {
//
//        PersoanaOficiu persoanaOficiu = persoanaOficiuDBRepo.getOneByUsername(username);
//        if (persoanaOficiu != null && persoanaOficiu.getPassword().equals(password)) {
//            return persoanaOficiu;
//        }
//        return null;
//    }

}
