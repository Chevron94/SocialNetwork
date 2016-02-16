package network.dialogs;

import network.dto.MessageDto;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
/**
 * Created by roman on 10/4/15.
 */
@ServerEndpoint(value = "/dialog", encoders = {DialogMessageEncoder.class}, decoders = {DialogMessageDecoder.class})
public class DialogEndpoint {
    private final Logger log = Logger.getLogger(getClass().getName());
    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void open(final Session session) {

        peers.add(session);
    }

    @OnMessage
    public void onMessage(final Session session, final MessageDto chatMessage) {
        try {
            for (Session s : peers) {
                if (s.isOpen() //add check receivers
                        ) {
                    s.getBasicRemote().sendObject(chatMessage);
                }
            }
        } catch (IOException | EncodeException e) {
            log.log(Level.WARNING, "onMessage failed", e);
        }
    }

    @OnClose
    public void close(final Session session)
    {
        peers.remove(session);
    }
}
