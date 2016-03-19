package network.service.events;

import network.entity.Message;

import java.io.Serializable;

/**
 * Created by Роман on 14.03.2016.
 */
public class MessageEvent {
    Message message;

    public MessageEvent() {
    }

    public MessageEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
