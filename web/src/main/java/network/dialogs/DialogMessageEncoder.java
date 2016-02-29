package network.dialogs;

/**
 * Created by roman on 10/4/15.
 */
import network.dto.MessageDto;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.text.SimpleDateFormat;

public class DialogMessageEncoder implements Encoder.Text<MessageDto> {
    @Override
    public void init(final EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public String encode(final MessageDto chatMessage) throws EncodeException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return Json.createObjectBuilder()
                .add("messageText", chatMessage.getMessageText())
                .add("sender", chatMessage.getSender())
                .add("receiver", chatMessage.getReceiverDialog())
                .add("received", simpleDateFormat.format(chatMessage.getReceivedDate())).build()
                .toString();
    }
}
