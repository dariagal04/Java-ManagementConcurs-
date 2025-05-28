package src.mpp2024.objectProtocol;

import src.mpp2024.domain.CategorieVarsta;
import src.mpp2024.domain.NumeProba;

import java.io.Serializable;

public class GetParticipantsByProbaAndCategorieRequest implements Serializable ,Request {
    private final NumeProba proba;
    private final CategorieVarsta categorie;

    public GetParticipantsByProbaAndCategorieRequest(NumeProba proba, CategorieVarsta categorie) {
        this.proba = proba;
        this.categorie = categorie;
    }

    public NumeProba getProba() {
        return proba;
    }

    public CategorieVarsta getCategorie() {
        return categorie;
    }
}
