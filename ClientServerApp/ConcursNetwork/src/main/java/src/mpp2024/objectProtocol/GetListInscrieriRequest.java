package src.mpp2024.objectProtocol;

import src.mpp2024.dto.ListInscrieriProbaDTO;

public class GetListInscrieriRequest implements Request {
    private final ListInscrieriProbaDTO listInscrieriProbaDTO;

    public GetListInscrieriRequest(final ListInscrieriProbaDTO inscriere) {this.listInscrieriProbaDTO = inscriere;}

    public ListInscrieriProbaDTO getListInscrieriProbaDTO() {return listInscrieriProbaDTO;}
}
