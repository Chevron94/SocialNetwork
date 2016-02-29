package network.dto;

import java.util.Date;

/**
 * Created by roman on 10/4/15.
 */
public class MessageDto {
    private String id;
    private String messageText;
    private String sender;
    private String receiverDialog;
    private Date receivedDate;
    private String received;

    public MessageDto() {
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceiverDialog() {
        return receiverDialog;
    }

    public void setReceiverDialog(String receiverDialog) {
        this.receiverDialog = receiverDialog;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "id='" + id + '\'' +
                ", messageText='" + messageText + '\'' +
                ", sender='" + sender + '\'' +
                ", receiverDialog='" + receiverDialog + '\'' +
                ", receivedDate='" + receivedDate + '\'' +
                '}';
    }
}
