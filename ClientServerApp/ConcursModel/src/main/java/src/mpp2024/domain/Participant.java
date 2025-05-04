package src.mpp2024.domain;


import src.mpp2024.domain.entity.Entitate;

import java.io.Serializable;

public class Participant extends Entitate<Integer> implements Serializable {
    protected String nume;
    protected int varsta;
    protected String cnp;
    protected int idPersoanaOficiu;



    public Participant(int id, String nume, int varsta, String cnp, int idPersoanaOficiu) {
        super(id);
        this.nume = nume;
        this.varsta = varsta;
        this.cnp = cnp;
        this.idPersoanaOficiu = idPersoanaOficiu;
    }


    public String getNume() {
        return nume;
    }

    public int getVarsta() {
        return varsta;
    }


    public String getCnp() {
        return cnp;
    }

    public int getIdPersoanaOficiu() {
        return idPersoanaOficiu;
    }


    @Override
    public String toString() {
        return nume+" "+varsta;
    }
}