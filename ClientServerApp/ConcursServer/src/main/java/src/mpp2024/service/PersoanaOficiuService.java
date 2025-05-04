package src.mpp2024.service;

import src.mpp2024.domain.*;
import src.mpp2024.repo.DB.PersoanaOficiuDBRepo;
import src.mpp2024.services.IConcursService;

import java.util.List;
import java.util.Map;

public class PersoanaOficiuService implements IConcursService {

    private final PersoanaOficiuDBRepo persoanaOficiuDBRepo;

    public PersoanaOficiuService(PersoanaOficiuDBRepo persoanaOficiuDBRepo) {
        this.persoanaOficiuDBRepo = persoanaOficiuDBRepo;
    }

    public PersoanaOficiu authenticate(String username, String password) {

        PersoanaOficiu persoanaOficiu = persoanaOficiuDBRepo.getOneByUsername(username);
        if (persoanaOficiu != null && persoanaOficiu.getPassword().equals(password)) {
            return persoanaOficiu;
        }
        return null;
    }

    @Override
    public PersoanaOficiu login(String username, String password) throws Exception {
        return authenticate(username, password);
    }

    @Override
    public List<Inscriere> getInscrieriProba(int probaId) throws Exception {
        return List.of();
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
}
