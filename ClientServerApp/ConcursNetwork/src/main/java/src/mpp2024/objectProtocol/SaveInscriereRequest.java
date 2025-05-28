package src.mpp2024.objectProtocol;

import src.mpp2024.domain.Inscriere;

import java.io.Serializable;
import java.security.PublicKey;

public class SaveInscriereRequest implements Serializable ,Request {
    public Inscriere inscriere;
    public SaveInscriereRequest(Inscriere inscriere) {
        this.inscriere = inscriere;
    }

    public Inscriere getInscriere() {
        return inscriere;
    }
}
