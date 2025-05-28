package src.mpp2024.objectProtocol;

import src.mpp2024.domain.CategorieVarsta;

import java.io.Serializable;

public class GetCategorieResponse implements Serializable ,Response{

    public CategorieVarsta categorie;
    public GetCategorieResponse(CategorieVarsta categorie) {
        this.categorie = categorie;
    }

    public CategorieVarsta getCategorie() {
        return categorie;
    }
}
