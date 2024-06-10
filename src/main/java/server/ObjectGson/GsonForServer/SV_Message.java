package server.ObjectGson.GsonForServer;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class SV_Message {
    @Id
    @Column(name = "userId")
    private int userID;
    @Column(name = "content")
    private String content;

    public SV_Message(int userID, String content) {
        this.userID = userID;
        this.content = content;
    }

    public SV_Message() {

    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SV_Message{" +
                "userID=" + userID +
                ", content='" + content + '\'' +
                '}';
    }
}
