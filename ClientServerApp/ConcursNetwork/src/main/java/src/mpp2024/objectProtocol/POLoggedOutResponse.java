package src.mpp2024.objectProtocol;

import src.mpp2024.dto.PersoanaOficiuDTO;

public class POLoggedOutResponse implements UpdateResponse{

    private final PersoanaOficiuDTO persoanaOficiu;
    public POLoggedOutResponse(final PersoanaOficiuDTO persoanaOficiu) {this.persoanaOficiu = persoanaOficiu;}
    public PersoanaOficiuDTO getPersoanaOficiu() {return persoanaOficiu;}

}
