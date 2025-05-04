package src.mpp2024.domain.entity;

import java.io.Serializable;

public abstract class Entitate<IdType> implements Serializable {
    private IdType id;
    public Entitate() {}
    public Entitate(IdType id) {
        this.id = id;
    }
    public IdType getId() {
        return id;
    }
    public void setId(IdType id) {
        this.id = id;
    }
}