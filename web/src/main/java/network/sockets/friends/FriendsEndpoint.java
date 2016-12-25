package network.sockets.friends;

import network.dao.FriendRequestDao;
import network.dao.UserDao;
import network.entity.FriendRequest;
import network.service.events.FriendEvent;
import network.service.events.FriendRequestEvent;
import network.sockets.PeersStorage;
import network.sockets.Sessions;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.event.Observes;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created by Роман on 27.09.2016.
 */
@Stateful
@ServerEndpoint(value = "/sockets/friends", encoders = {FriendsMessageEncoder.class}, decoders = {FriendsMessageDecoder.class})
public class FriendsEndpoint {

    @EJB
    FriendRequestDao friendRequestService;
    @EJB
    UserDao userService;

    @OnOpen
    public void open(final Session session) {
    }

    @OnClose
    public void close(final Session session) {

    }

    @OnMessage
    public void onMessage(final Session session, final FriendsDTO friendsDTO){
        if (friendsDTO.getReceiver() == null) {
            Sessions sessions = PeersStorage.getPeers().get(friendsDTO.getSender());
            if (sessions == null) {
                sessions = new Sessions();
            }
            sessions.setFriendsSession(session);
        } else {
            FriendRequest friendRequest = friendRequestService.getFriendRequestByTwoUsersId(friendsDTO.getSender(), friendsDTO.getReceiver());
            if (friendsDTO.getAccept()) {
                if (friendRequest == null) {
                    friendRequest = new FriendRequest(userService.getUserById(friendsDTO.getSender()),
                                                      userService.getUserById(friendsDTO.getReceiver()),
                                                              false);
                    friendRequestService.create(friendRequest);
                } else {
                    if (friendRequest.getSender().getId().equals(friendsDTO.getReceiver()) &&
                            friendRequest.getReceiver().getId().equals(friendsDTO.getSender())){
                        friendRequest.setConfirmed(true);
                        friendRequestService.update(friendRequest);
                    }
                }
            } else {
                if (friendRequest == null) {
                    return;
                }
                friendRequestService.delete(friendRequest.getId());
            }
        }
    }

    public void friendRequestEvent(@Observes @FriendRequestEvent FriendEvent friendRequestEvent) throws IOException, EncodeException {
        FriendRequest friendRequest = friendRequestEvent.getFriendRequest();
        Sessions sessions = PeersStorage.getPeers().get(friendRequest.getReceiver().getId());
        if (sessions!= null && sessions.getFriendsSession() != null) {
            sessions.getFriendsSession().getBasicRemote().sendObject(friendRequest);
        }
    }
}
