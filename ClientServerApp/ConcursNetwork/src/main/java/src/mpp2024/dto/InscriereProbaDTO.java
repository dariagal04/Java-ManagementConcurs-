package src.mpp2024.dto;

import java.io.Serializable;

public class InscriereProbaDTO implements Serializable {
    private Integer id;
    private int idParticipant;
    private int idProba;
    private int idCategorie;

    public InscriereProbaDTO(Integer id, int idParticipant, int idProba, int idCategorie) {
        this.id = id;
        this.idParticipant = idParticipant;
        this.idProba = idProba;
        this.idCategorie = idCategorie;
    }

    public Integer getId() {
        return id;
    }

    public int getIdParticipant() {
        return idParticipant;
    }

    public int getIdProba() {
        return idProba;
    }

    public int getIdCategorie() {
        return idCategorie;
    }
}
