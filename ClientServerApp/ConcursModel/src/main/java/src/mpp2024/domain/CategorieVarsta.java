package src.mpp2024.domain;


import src.mpp2024.domain.entity.Entitate;

public class CategorieVarsta extends Entitate<Integer> {
    private int varstaMin;
    private int varstaMax;

    public CategorieVarsta() {}
    public CategorieVarsta(Integer id, int varstaMin, int varstaMax) {
        super(id);
        this.varstaMin = varstaMin;
        this.varstaMax = varstaMax;
    }

    public int getVarstaMinima() {
        return varstaMin;
    }

    public int getVarstaMaxima() {
        return varstaMax;
    }

    public void setVarstaMinima(int varstaMin) {
        this.varstaMin = varstaMin;
    }

    public void setVarstaMaxima(int varstaMax) {
        this.varstaMax = varstaMax;
    }

    public String toString() {
        return varstaMin + "-" + varstaMax;
    }
}