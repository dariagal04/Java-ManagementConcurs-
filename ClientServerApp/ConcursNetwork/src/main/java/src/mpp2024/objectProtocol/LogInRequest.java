package src.mpp2024.objectProtocol;

import src.mpp2024.dto.PersoanaOficiuDTO;

import java.io.Serializable;

public class LogInRequest implements Request, Serializable {

    private final PersoanaOficiuDTO persoanaOficiuDTO;

    public LogInRequest(PersoanaOficiuDTO persoanaOficiuDTO) {this.persoanaOficiuDTO = persoanaOficiuDTO;}

    public PersoanaOficiuDTO getPersoanaOficiuDTO() {return persoanaOficiuDTO;}

}
