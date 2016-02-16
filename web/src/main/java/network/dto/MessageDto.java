package network.dto;

/**
 * Created by roman on 10/4/15.
 */
public class MessageDto {
    private String messageText;
    private String sender;
    private String received;

    public MessageDto() {
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

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "messageText='" + messageText + '\'' +
                ", sender='" + sender + '\'' +
                ", received=" + received +
                '}';
    }
}
