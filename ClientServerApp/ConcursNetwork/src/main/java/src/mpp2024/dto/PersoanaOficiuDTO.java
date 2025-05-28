package src.mpp2024.dto;
import src.mpp2024.domain.PersoanaOficiu;

import java.io.Serializable;

public class PersoanaOficiuDTO implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String locatie_oficiu;
    public PersoanaOficiuDTO(int id, String username, String password, String locatie_oficiu) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.locatie_oficiu = locatie_oficiu;
    }

    public PersoanaOficiuDTO(String username, String password) {
        this.username = username;
        this.password = password;
        this.locatie_oficiu = locatie_oficiu;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}