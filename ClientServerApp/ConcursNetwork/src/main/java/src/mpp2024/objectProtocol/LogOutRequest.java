package src.mpp2024.objectProtocol;

import src.mpp2024.dto.PersoanaOficiuDTO;

import java.io.Serializable;

public class LogOutRequest implements Request , Serializable{
    private final PersoanaOficiuDTO persoanaOficiuDTO;

    public LogOutRequest(PersoanaOficiuDTO persoanaOficiuDTO) {this.persoanaOficiuDTO = persoanaOficiuDTO;}

    public PersoanaOficiuDTO getPersoanaOficiuDTO() {return persoanaOficiuDTO;}
}
