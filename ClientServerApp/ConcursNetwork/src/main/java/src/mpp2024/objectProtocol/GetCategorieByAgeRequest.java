package src.mpp2024.objectProtocol;

import java.io.Serializable;

public class GetCategorieByAgeRequest implements Serializable ,Request {

    public int age;
    public GetCategorieByAgeRequest(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
