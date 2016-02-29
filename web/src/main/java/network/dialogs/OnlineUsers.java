package network.dialogs;

import network.entity.User;

import javax.websocket.Session;

/**
 * Created by roman on 10/20/15.
 */
public class OnlineUsers {
    private Session session;
    private User user;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
