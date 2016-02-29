package network.dialogs;

import network.dao.DialogDao;
import network.dao.MessageDao;
import network.dao.UserDao;
import network.dao.UserDialogDao;
import network.dto.MessageDto;
import network.entity.Dialog;
import network.entity.Message;
import network.entity.User;
import network.entity.UserDialog;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by roman on 10/4/15.
 */
@ServerEndpoint(value = "/dialog", encoders = {DialogMessageEncoder.class}, decoders = {DialogMessageDecoder.class})
public class DialogEndpoint {
    private final Logger log = Logger.getLogger(getClass().getName());
    private static Map<User,Session> peers = Collections.synchronizedMap(new HashMap<User, Session>());

    @Inject
    DialogDao dialogService;
    @Inject
    UserDao userService;
    @Inject
    UserDialogDao userDialogService;
    @Inject
    MessageDao messageService;

    public void setDialogService(DialogDao dialogService) {
        this.dialogService = dialogService;
    }

    public void setUserService(UserDao userService) {
        this.userService = userService;
    }

    public void setUserDialogService(UserDialogDao userDialogService) {
        this.userDialogService = userDialogService;
    }

    public void setMessageService(MessageDao messageService) {
        this.messageService = messageService;
    }

    @OnOpen
    public void open(final Session session) {

    }

    @OnMessage
    public void onMessage(final Session session, final MessageDto chatMessage) {
        try {
            if (chatMessage.getId() != null && !chatMessage.getId().equals("")) {
                peers.put(userService.getUserByLogin(chatMessage.getSender()),session);
            } else {
                Dialog d = dialogService.getDialogById(Long.valueOf(chatMessage.getReceiverDialog()));
                messageService.create(new Message(userService.getUserByLogin(chatMessage.getSender()), d, chatMessage.getMessageText(), chatMessage.getReceivedDate(),false));
                List<UserDialog> userDialogs = userDialogService.getUsersByDialog(d);
                List<Session> sessions = new ArrayList<>();
                for (UserDialog ud:userDialogs){
                    if (peers.get(ud.getUser())!=null){
                        sessions.add(peers.get(ud.getUser()));
                    }
                }
                for (Session s: sessions) {
                    if (s.isOpen()) {
                        s.getBasicRemote().sendObject(chatMessage);
                    }
                }
            }
        } catch (IOException | EncodeException e) {
            log.log(Level.WARNING, "onMessage failed", e);
        }
    }

    @OnClose
    public void close(final Session session) {
        peers.remove(session);
    }
}
