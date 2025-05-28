package src.mpp2024.objectProtocol;

import src.mpp2024.dto.PersoanaOficiuDTO;

import java.io.Serializable;

public class POLoggedInResponse implements UpdateResponse, Serializable {

    private final PersoanaOficiuDTO persoanaOficiu;
    public POLoggedInResponse(final PersoanaOficiuDTO persoanaOficiu) {this.persoanaOficiu = persoanaOficiu;}
    public PersoanaOficiuDTO getPersoanaOficiu() {return persoanaOficiu;}
}
