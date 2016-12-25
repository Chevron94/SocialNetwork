package network.sockets.online;

import network.dao.FriendRequestDao;
import network.dao.UserDao;
import network.entity.FriendRequest;
import network.entity.User;
import network.entity.UserDialog;
import network.sockets.PeersStorage;
import network.sockets.Sessions;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Роман on 25.09.2016.
 */
@Stateful
@ServerEndpoint(value = "/sockets/online", encoders = {OnlineMessageEncoder.class}, decoders = {OnlineMessageDecoder.class})
public class OnlineEndpoint {
    @EJB
    UserDao userService;
    @EJB
    FriendRequestDao friendRequestService;

    @OnOpen
    public void open(final Session session) {
    }

    @OnClose
    public void close(final Session session) throws IOException, EncodeException {
        Long userId = PeersStorage.getUserIdBySession(session);
        if (userId != null ) {
            User user =userService.getUserById(userId);
            if (user != null) {
                user.setOnline(false);
                userService.update(user);
                PeersStorage.remove(userId);
                for (Map.Entry<Long, Sessions> entry: PeersStorage.getPeers().entrySet()){
                    if (entry.getValue().getOnlineSession() != null)
                        entry.getValue().getOnlineSession().getBasicRemote().sendObject(new OnlineMessageDTO(userId, false));
                }
            }
        }
    }

    @OnMessage
    public void onMessage(final Session session, final Long id) throws IOException, EncodeException {
        Sessions sessions = PeersStorage.getPeers().get(id);
        if (sessions == null) {
            sessions = new Sessions();
        }
        sessions.setOnlineSession(session);
        User user = userService.getUserById(id);
        user.setOnline(true);
        userService.update(user);
        PeersStorage.getPeers().put(id, sessions);

        for (Map.Entry<Long, Sessions> entry: PeersStorage.getPeers().entrySet()){
            if (entry.getValue().getOnlineSession() != null) {
                entry.getValue().getOnlineSession().getBasicRemote().sendObject(new OnlineMessageDTO(id,true));
            }
        }
    }
}
