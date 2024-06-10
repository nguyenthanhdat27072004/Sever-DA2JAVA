package server.ObjectGson.GsonForServer;

import javax.persistence.*;


@Entity
@Table(name = "rank")
public class SV_Rank {
    @Id
    @Column(name = "userId")
    private int userId;

    @Column(name = "rank", nullable = false)
    private String rank;

    public SV_Rank() {
    }

    public SV_Rank(int userId, String rank) {
        this.userId = userId;
        this.rank = rank;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Rank{" +
                "userId=" + userId +
                ", rank='" + rank + '\'' +
                '}';
    }
}
