package src.mpp2024.objectProtocol;

import src.mpp2024.dto.PersoanaOficiuDTO;

import java.io.Serializable;

public class FindPOByUsernamePasswordResponse implements Response,Serializable {
    private PersoanaOficiuDTO persoanaOficiu;
    public FindPOByUsernamePasswordResponse(final PersoanaOficiuDTO persoanaOficiu){
        this.persoanaOficiu = persoanaOficiu;
    }
    public PersoanaOficiuDTO getPersoanaOficiu() {return persoanaOficiu;}
}
