package src.mpp2024.domain;

import src.mpp2024.domain.entity.Entitate;

public class Inscriere extends Entitate<Integer> {
    private int idParticipant;
    private int idProba;
    private int idCategorie;
    public Inscriere(int idParticipant, int idProba,int idCategorie) {
        //super(id);
        this.idParticipant = idParticipant;
        this.idProba = idProba;
        this.idCategorie = idCategorie;
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

    @Override
    public String toString() {
        return "Inscriere [idParticipant=" + idParticipant + ", idProba=" + idProba + ", idCategorie=" + idCategorie + "]";
    }
}