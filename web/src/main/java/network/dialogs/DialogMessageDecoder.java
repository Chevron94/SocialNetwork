package network.dialogs;

import network.dto.MessageDto;
import org.apache.commons.lang3.StringUtils;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by roman on 10/4/15.
 */
public class DialogMessageDecoder implements Decoder.Text<MessageDto> {
    @Override
    public void init(final EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public MessageDto decode(final String textMessage) throws DecodeException {
        MessageDto message = new MessageDto();
        String tmp = textMessage.replaceAll("\n","\\\\n\\\\r");
        JsonObject obj = Json.createReader(new StringReader(tmp))
                .readObject();
        try {
            message.setMessageText(obj.getString("messageText"));
            message.setMessageText(StringUtils.replaceEach(obj.getString("messageText"), new String[]{"&", "\"", "<", ">", "'", "/",}, new String[]{"&amp;", "&quot;", "&lt;", "&gt;", "&apos;", "&#x2F;"}));
            message.setReceiverDialog(obj.getString("dialog"));
        }catch (NullPointerException e){
            message.setId((obj.getString("id")));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        message.setSender(obj.getString("sender"));
        Date date = new Date();
        message.setReceivedDate((date));
        message.setReceived(simpleDateFormat.format(date));
        System.out.println(message);
        return message;
    }

    @Override
    public boolean willDecode(final String s) {
        return true;
    }
}
