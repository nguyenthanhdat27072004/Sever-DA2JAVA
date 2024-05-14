package server.model;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "senderId", referencedColumnName = "userId")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiverId", referencedColumnName = "userId")
    private User receiver;

    @Column(nullable = false, length = 500)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public Message() {
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
