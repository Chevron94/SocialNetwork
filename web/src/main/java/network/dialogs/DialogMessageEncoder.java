package network.dialogs;

/**
 * Created by roman on 10/4/15.
 */
import network.dto.MessageDto;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class DialogMessageEncoder implements Encoder.Text<MessageDto> {
    @Override
    public void init(final EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public String encode(final MessageDto chatMessage) throws EncodeException {
        return Json.createObjectBuilder()
                .add("messageText", chatMessage.getMessageText())
                .add("sender", chatMessage.getSender())
                .add("received", chatMessage.getReceived().toString()).build()
                .toString();
    }
}
