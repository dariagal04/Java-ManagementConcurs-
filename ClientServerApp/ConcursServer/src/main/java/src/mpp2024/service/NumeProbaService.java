package src.mpp2024.service;

import src.mpp2024.domain.*;
import src.mpp2024.repo.DB.NumeProbaDBRepo;
import src.mpp2024.services.ConcursException;
import src.mpp2024.services.IConcursObserver;
import src.mpp2024.services.IConcursService;

import java.util.List;
import java.util.Map;

public class NumeProbaService implements IConcursService {

    private final NumeProbaDBRepo numeProbaDBRepo;

    public NumeProbaService(NumeProbaDBRepo numeProbaDBRepo) {
        this.numeProbaDBRepo = numeProbaDBRepo;
    }

    public NumeProba getNumeProba(String nume){
        return numeProbaDBRepo.getNumeProbaByName(nume);
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
    public void login(PersoanaOficiu persoanaOficiu, IConcursObserver client) throws ConcursException {

    }

    @Override
    public PersoanaOficiu getPersoanaOficiuByUsernamePassword(String username, String password) throws ConcursException {
        return null;
    }

    @Override
    public void logout(PersoanaOficiu persoanaOficiu, IConcursObserver client) throws ConcursException {

    }

    @Override
    public Map<String, Map<String, Integer>> getCompetitionsWithParticipants() {
        return Map.of();
    }
}
