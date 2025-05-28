package src.mpp2024.objectProtocol;

import src.mpp2024.domain.Inscriere;
import src.mpp2024.domain.InscriereProba;
import src.mpp2024.dto.InscriereProbaDTO;

import java.io.Serializable;

public class NewInscriereResponse implements Serializable ,UpdateResponse{
    private final Inscriere inscriere;

    public NewInscriereResponse(Inscriere inscriere) {
        this.inscriere = inscriere;
    }

    public Inscriere getInscriere() {
        return inscriere;
    }
}
