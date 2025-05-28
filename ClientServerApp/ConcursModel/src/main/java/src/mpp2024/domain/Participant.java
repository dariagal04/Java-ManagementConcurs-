package src.mpp2024.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Table;
import src.mpp2024.domain.entity.Entitate;

import java.io.Serializable;
@jakarta.persistence.Entity
@Table(name="participanti")
public class Participant extends Entitate<Integer> implements Serializable {

    @Column(name = "nume",nullable = false)
    protected String nume;
    @Column(name = "varsta",nullable = false)
    protected int varsta;
    @Column(name = "cnp",nullable = false)
    protected String cnp;
    @Column(name = "persoanaOficiu_id",nullable = false)
    protected int idPersoanaOficiu;


    public Participant(){}
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