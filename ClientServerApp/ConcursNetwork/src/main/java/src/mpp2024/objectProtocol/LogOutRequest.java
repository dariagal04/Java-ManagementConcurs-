package src.mpp2024.objectProtocol;

import src.mpp2024.dto.PersoanaOficiuDTO;

public class LogOutRequest implements Request {
    private final PersoanaOficiuDTO persoanaOficiuDTO;

    public LogOutRequest(PersoanaOficiuDTO persoanaOficiuDTO) {this.persoanaOficiuDTO = persoanaOficiuDTO;}

    public PersoanaOficiuDTO getPersoanaOficiuDTO() {return persoanaOficiuDTO;}
}
