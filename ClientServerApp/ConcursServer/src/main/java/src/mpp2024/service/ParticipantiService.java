package src.mpp2024.service;

import src.mpp2024.domain.*;
import src.mpp2024.messaging.RabbitPublisher;
import src.mpp2024.repo.DB.ParticipantiDBRepo;
import src.mpp2024.repo.Interfete.IParticipantRepo;
import src.mpp2024.services.ConcursException;
import src.mpp2024.services.IConcursObserver;
import src.mpp2024.services.IConcursService;

import java.util.List;
import java.util.Map;

public class ParticipantiService implements IConcursService {

    private final IParticipantRepo participantiDBRepo;

    public ParticipantiService(IParticipantRepo participantiDBRepo) {
        this.participantiDBRepo = participantiDBRepo;
    }

    public boolean saveEntity(Participant participant) {
        RabbitPublisher.sendNotification("Inscriere nouă: participant " + participant.getNume());
        return participantiDBRepo.saveEntity(participant);
    }

    @Override
    public boolean saveEntityi(Inscriere i) {
        return false;
    }


    public Participant getParticipantByNume(String nume) {
        return participantiDBRepo.getParticipantByName(nume);
    }

    public Participant getParticipantByCNP(String cnp) {
        return participantiDBRepo.getParticipantByCNP(cnp);
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
}
