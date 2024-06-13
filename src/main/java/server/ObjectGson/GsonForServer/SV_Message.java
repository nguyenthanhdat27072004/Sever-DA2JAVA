package server.ObjectGson.GsonForServer;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class SV_Message {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "idMess")
    private int idMess;
    @Column(name = "userId")
    private int userID;
    @Column(name = "content")
    private String content;

    public int getIdMess() {
        return idMess;
    }

    public void setIdMess(int idMess) {
        this.idMess = idMess;
    }

    public SV_Message (int userID, int idMess, String content){
        this.content= content;
        this.idMess= idMess;
        this.userID=userID;
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
                "idMess=" + idMess +
                ", userID=" + userID +
                ", content='" + content + '\'' +
                '}';
    }
}
