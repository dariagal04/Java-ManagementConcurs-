package src.mpp2024.service;

import src.mpp2024.domain.CategorieVarsta;
import src.mpp2024.domain.*;
import src.mpp2024.domain.Participant;
import src.mpp2024.repo.DB.InscriereDBRepo;
import src.mpp2024.services.ConcursException;
import src.mpp2024.services.IConcursObserver;
import src.mpp2024.services.IConcursService;

import java.util.List;
import java.util.Map;

public class InscriereService implements IConcursService {

    private final InscriereDBRepo inscriereDBRepo;

    public InscriereService(InscriereDBRepo inscriereDBRepo) {
        this.inscriereDBRepo = inscriereDBRepo;
    }

    public Map<String, Map<String, Integer>> getCompetitionsWithParticipants() {
        return inscriereDBRepo.getCompetitionsWithParticipants();
    }

    @Override
    public NumeProba getNumeProba(String selectedProba) {
        return null;
    }

    @Override
    public CategorieVarsta getCategorie(int i) {
        return null;
    }

    public List<Participant> getParticipantsByProbaAndCategorie(NumeProba proba, CategorieVarsta categorie) {
        return inscriereDBRepo.getParticipantsByProbaAndCategorie(proba, categorie);
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
        return inscriereDBRepo.saveEntity(i);
    }


    public boolean saveEntity(Inscriere inscriere) {
        return inscriereDBRepo.saveEntity(inscriere);
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
}
