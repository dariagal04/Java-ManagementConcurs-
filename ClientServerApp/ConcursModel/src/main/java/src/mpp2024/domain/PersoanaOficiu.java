package src.mpp2024.domain;


import src.mpp2024.domain.entity.Entitate;

import java.io.Serializable;

public class PersoanaOficiu extends Entitate<Integer> implements Serializable {
    private String username;
    private String password;
    private String locatie_oficiu;
    public PersoanaOficiu(int id, String username, String password, String locatie_oficiu) {
        super(id);
        this.username = username;
        this.password = password;
        this.locatie_oficiu = locatie_oficiu;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getLocatie_oficiu() {
        return locatie_oficiu;
    }
    public void setLocatie_oficiu(String locatie_oficiu) {
        this.locatie_oficiu = locatie_oficiu;
    }

    @Override
    public String toString() {
        return "Persoana:"+username + " Locatie" + locatie_oficiu;
    }
}