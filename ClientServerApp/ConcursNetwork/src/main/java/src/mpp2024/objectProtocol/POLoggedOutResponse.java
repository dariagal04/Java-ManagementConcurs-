package src.mpp2024.objectProtocol;

import src.mpp2024.dto.PersoanaOficiuDTO;

import java.io.Serializable;

public class POLoggedOutResponse implements UpdateResponse, Serializable {

    private final PersoanaOficiuDTO persoanaOficiu;
    public POLoggedOutResponse(final PersoanaOficiuDTO persoanaOficiu) {this.persoanaOficiu = persoanaOficiu;}
    public PersoanaOficiuDTO getPersoanaOficiu() {return persoanaOficiu;}

}
