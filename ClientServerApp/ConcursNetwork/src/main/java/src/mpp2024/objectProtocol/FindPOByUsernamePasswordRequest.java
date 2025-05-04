package src.mpp2024.objectProtocol;

import src.mpp2024.dto.PersoanaOficiuDTO;

public class FindPOByUsernamePasswordRequest implements Request{
    private PersoanaOficiuDTO persoanaOficiu;

    public FindPOByUsernamePasswordRequest(final PersoanaOficiuDTO persoanaOficiu) {
        this.persoanaOficiu = persoanaOficiu;
    }

    public PersoanaOficiuDTO getPersoanaOficiu() {return persoanaOficiu;}
}
