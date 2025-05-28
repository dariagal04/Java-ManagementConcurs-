package src.mpp2024.objectProtocol;

import java.io.Serializable;

public class GetCategorieRequest implements Serializable ,Request {

    public int id;
    public GetCategorieRequest(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
