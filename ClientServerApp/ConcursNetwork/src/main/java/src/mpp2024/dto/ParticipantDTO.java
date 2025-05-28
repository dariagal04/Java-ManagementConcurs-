package src.mpp2024.dto;

import java.io.Serializable;

public class ParticipantDTO implements Serializable {
    private Integer id;
    protected String nume;
    protected int varsta;
    protected String cnp;
    protected int idPersoanaOficiu;
    public ParticipantDTO(Integer id, String nume, int varsta, String cnp, int idPersoanaOficiu) {
        this.id = id;
        this.nume = nume;
        this.varsta = varsta;
        this.cnp = cnp;
        this.idPersoanaOficiu = idPersoanaOficiu;
    }

    public Integer getId() {
        return id;
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
}
