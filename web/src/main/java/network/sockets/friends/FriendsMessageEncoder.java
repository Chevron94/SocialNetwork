package network.sockets.friends;

import network.entity.FriendRequest;
import network.entity.User;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Created by Роман on 27.09.2016.
 */
public class FriendsMessageEncoder implements Encoder.Text<FriendRequest> {
    @Override
    public String encode(FriendRequest friendRequest) throws EncodeException {
        return friendRequest.toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
