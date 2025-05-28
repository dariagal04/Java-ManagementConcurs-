package src.mpp2024.objectProtocol;

import src.mpp2024.domain.NumeProba;

import java.io.Serializable;

public class GetNumeProbaResponse implements Serializable ,Response{
    private final NumeProba numeProba;
    public GetNumeProbaResponse(final NumeProba numeProba) {
        this.numeProba = numeProba;
    }

    public NumeProba getNumeProba() {
        return numeProba;
    }

}
