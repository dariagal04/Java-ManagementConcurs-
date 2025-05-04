package src.mpp2024.objectProtocol;

import src.mpp2024.dto.PersoanaOficiuDTO;

public class LogInRequest implements Request {

    private final PersoanaOficiuDTO persoanaOficiuDTO;

    public LogInRequest(PersoanaOficiuDTO persoanaOficiuDTO) {this.persoanaOficiuDTO = persoanaOficiuDTO;}

    public PersoanaOficiuDTO getPersoanaOficiuDTO() {return persoanaOficiuDTO;}

}
