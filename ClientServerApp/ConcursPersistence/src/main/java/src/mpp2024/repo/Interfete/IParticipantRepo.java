package src.mpp2024.repo.Interfete;

import src.mpp2024.domain.Participant;

import java.util.List;

public interface IParticipantRepo extends Repository<Participant, Integer> {


    Participant getParticipantByName(String name);
    List<Participant> getParticipantsByProba(int proba);
    List<Participant> getParticipantsByCategory(int IdCategorie);
}