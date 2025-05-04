package src.mpp2024.repo.Interfete;
import src.mpp2024.domain.Inscriere;

public interface IInscriereRepo extends Repository<Inscriere, Integer> {

    Inscriere getInscriereByParticipantAndProbaAndCategorie(int idParticipant, int idProba, int idCategorie);
}