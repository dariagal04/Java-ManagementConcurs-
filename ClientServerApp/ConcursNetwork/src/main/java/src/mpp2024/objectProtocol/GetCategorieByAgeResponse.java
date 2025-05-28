package src.mpp2024.objectProtocol;

import src.mpp2024.domain.CategorieVarsta;

import java.io.Serializable;

public class GetCategorieByAgeResponse implements Serializable ,Response{

    public CategorieVarsta category;
    public GetCategorieByAgeResponse(CategorieVarsta category) {
        this.category = category;
    }
    public CategorieVarsta GetCategorieVarsta() {
        return category;

    }
}
