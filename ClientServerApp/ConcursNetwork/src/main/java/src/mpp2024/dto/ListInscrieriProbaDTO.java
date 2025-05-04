package src.mpp2024.dto;

import src.mpp2024.domain.Inscriere;

import java.io.Serializable;
import java.util.List;

public class ListInscrieriProbaDTO implements Serializable {
    private List<Inscriere> inscrieriProbe;

    public ListInscrieriProbaDTO(List<Inscriere> inscrieriProbe) {
        this.inscrieriProbe = inscrieriProbe;
    }

    public List<Inscriere> getInscrieriProbe() {
        return inscrieriProbe;
    }
}