package src.mpp2024.service;

import src.mpp2024.domain.*;
import src.mpp2024.repo.DB.CategorieDBRepo;
import src.mpp2024.services.ConcursException;
import src.mpp2024.services.IConcursObserver;
import src.mpp2024.services.IConcursService;

import java.util.List;
import java.util.Map;
//import src.mpp2024.services.IConcursService;
//import src.mpp2024.services.IConcursService;

public class CategorieService implements IConcursService {

    private final CategorieDBRepo categorieDBRepo;

    public CategorieService(CategorieDBRepo categorieDBRepo) {
        this.categorieDBRepo = categorieDBRepo;
    }

    public CategorieVarsta getCategorie(int id) {
        return categorieDBRepo.getOne(id);
    }

    @Override
    public List<Participant> getParticipantsByProbaAndCategorie(NumeProba proba, CategorieVarsta categorie) {
        return List.of();
    }


    public CategorieVarsta getCategorieVarstaByAgeGroup(int min, int max) {
        return categorieDBRepo.getCategorieVarstaByAgeGroup(min, max);
    }

    public CategorieVarsta getCategorieVarstaByAge(int varsta) {
        return categorieDBRepo.getCategorieVarstaByAge(varsta);
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

    @Override
    public NumeProba getNumeProba(String selectedProba) {
        return null;
    }
}
