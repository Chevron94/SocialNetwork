package network.sockets.friends;

/**
 * Created by Роман on 01.10.2016.
 */
public class FriendsDTO {
    Long sender;
    Long receiver;
    Boolean accept;

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public Boolean getAccept() {
        return accept;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }
}
