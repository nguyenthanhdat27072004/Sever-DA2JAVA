package server.ObjectGson.GsonForServer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "level")
public class SV_Level {
    private int idUser;
    private int level;

    public SV_Level() {
    }

    public SV_Level(int idUser, int level) {
        this.idUser = idUser;
        this.level = level;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "SV_Level{" +
                "idUser=" + idUser +
                ", level=" + level +
                '}';
    }
}
