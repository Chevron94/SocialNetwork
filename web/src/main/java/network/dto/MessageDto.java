package network.dto;

import network.entity.Dialog;
import network.entity.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by roman on 10/4/15.
 */
public class MessageDto {
    private String id;
    private String messageText;
    private String sender;
    private String senderId;
    private String receiverDialog;
    private Date receivedDate;
    private String received;
    private Boolean read;
    private String avatar;

    public MessageDto() {
    }

    public MessageDto(Message message){
        id = message.getId().toString();
        messageText = message.getText();
        sender = message.getUser().getLogin();
        senderId = message.getUser().getId().toString();
        receiverDialog = message.getDialog().getId().toString();
        receivedDate = message.getDateTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        received = (simpleDateFormat.format(receivedDate));
        read = message.getRead();
        avatar = message.getUser().getPhotoURL();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
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

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "id='" + id + '\'' +
                ", messageText='" + messageText + '\'' +
                ", sender='" + sender + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverDialog='" + receiverDialog + '\'' +
                ", receivedDate=" + receivedDate +
                ", received='" + received + '\'' +
                '}';
    }
}
