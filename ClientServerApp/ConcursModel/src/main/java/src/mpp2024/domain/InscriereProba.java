package src.mpp2024.domain;


import src.mpp2024.domain.entity.Entitate;

import java.io.Serializable;

public class InscriereProba extends Entitate<Integer> implements Serializable {
    private int nrParticipanti;
    private String numeProba;
    private String categorieVarsta;

    public InscriereProba(int id, int nrParticipanti, String numeProba, String categorieVarsta) {
        super(id);
        this.nrParticipanti = nrParticipanti;
        this.numeProba = numeProba;
        this.categorieVarsta = categorieVarsta;
    }

    public int getNrParticipanti() {
        return nrParticipanti;
    }

    public String getNumeProba() {
        return numeProba;
    }

    public String getCategorieVarsta() {
        return categorieVarsta;
    }

    public String toString() {
        return "InscrieriProba{" +
                "nrParticipanti=" + nrParticipanti +
                ", numeProba='" + numeProba + '\'' +
                ", categorieVarsta='" + categorieVarsta + '\'' +
                '}';
    }
}