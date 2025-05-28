package src.mpp2024.objectProtocol;

import src.mpp2024.domain.NumeProba;

import java.io.Serializable;

public class GetNumeProbaRequest implements Serializable ,Request {
    private final String numeProba;
    public GetNumeProbaRequest(final String  numeProba) {
        this.numeProba = numeProba;
    }

    public String getNumeProba() {
        return numeProba;
    }

}
