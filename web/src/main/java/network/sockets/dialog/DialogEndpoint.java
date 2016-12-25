package network.sockets.dialog;

import network.dao.DialogDao;
import network.dao.MessageDao;
import network.dao.UserDao;
import network.dao.UserDialogDao;
import network.entity.Dialog;
import network.entity.Message;
import network.entity.UserDialog;
import network.service.events.MessageEvent;
import network.service.events.NewMessageEvent;
import network.service.events.ReadEvent;
import network.service.events.ReadMessageEvent;
import network.sockets.PeersStorage;
import network.sockets.Sessions;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.event.Observes;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by roman on 10/4/15.
 */
@ServerEndpoint(value = "/sockets/dialog", encoders = {DialogMessageEncoder.class}, decoders = {DialogMessageDecoder.class})
@Stateful
public class DialogEndpoint {
    private final Logger log = Logger.getLogger(getClass().getName());
    //private static Map<User,Session> peers = Collections.synchronizedMap(new HashMap<User, Session>());
    @EJB
    DialogDao dialogService;
    @EJB
    UserDao userService;
    @EJB
    UserDialogDao userDialogService;
    @EJB
    MessageDao messageService;

    @OnOpen
    public void open(final Session session) {

    }

    @OnMessage
    public void onMessage(final Session session, final MessageDto chatMessage) {
        try {
            if (chatMessage == null) {
                return;
            }
            if (chatMessage.getMessageText() == null  && chatMessage.getDialogId() == null) {
                Sessions sessions = PeersStorage.getPeers().get(Long.parseLong(chatMessage.getSenderId()));
                if (sessions == null) {
                    sessions = new Sessions();
                }
                sessions.setMessageSession(session);
            } else {
                if (chatMessage.getMessageText().equals("")) {
                    messageService.readMessages(Long.valueOf(chatMessage.getSenderId()), Long.valueOf(chatMessage.getDialogId()));
                } else {
                    Dialog d = dialogService.getDialogById(Long.valueOf(chatMessage.getDialogId()));
                    messageService.create(new Message(userService.getUserById(Long.valueOf(chatMessage.getSenderId())), d, chatMessage.getMessageText(), chatMessage.getReceivedDate(), false));
                    d.setLastMessageDate(chatMessage.getReceivedDate());
                    dialogService.update(d);
                }
            }
        } catch (Exception e) {
            log.log(Level.WARNING, "onMessage failed", e);
        }
    }

    public void newMessageEvent(@Observes @NewMessageEvent MessageEvent messageEvent){
        try {
            Message message = messageEvent.getMessage();
            MessageDto messageDto = new MessageDto(message);
            Dialog d = message.getDialog();
            List<UserDialog> userDialogs = userDialogService.getUsersByDialog(d);
            List<Session> sessions = new ArrayList<>();
            for (UserDialog ud : userDialogs) {
                if (PeersStorage.getPeers().get(ud.getUser().getId()) != null && PeersStorage.getPeers().get(ud.getUser().getId()).getMessageSession() != null) {
                    sessions.add(PeersStorage.getPeers().get(ud.getUser().getId()).getMessageSession());
                }
            }
            for (Session s : sessions) {
                if (s.isOpen()) {
                    s.getBasicRemote().sendObject(messageDto);
                }
            }
        } catch (EncodeException | IOException ignored) {

        }
    }

    public void readEvent(@Observes @ReadMessageEvent ReadEvent readEvent){
        try {
            MessageDto messageDto = new MessageDto();
            messageDto.setSenderId(readEvent.getIdUser().toString());
            messageDto.setDialogId(readEvent.getIdDialog().toString());
            messageDto.setMessageText("");
            Dialog d = dialogService.getDialogById(readEvent.getIdDialog());
            List<UserDialog> userDialogs = userDialogService.getUsersByDialog(d);
            List<Session> sessions = new ArrayList<>();
            for (UserDialog ud : userDialogs) {
                if (PeersStorage.getPeers().get(ud.getUser().getId()) != null && PeersStorage.getPeers().get(ud.getUser().getId()).getMessageSession() != null) {
                    sessions.add(PeersStorage.getPeers().get(ud.getUser().getId()).getMessageSession());
                }
            }
            for (Session s : sessions) {
                if (s.isOpen()) {
                    s.getBasicRemote().sendObject(messageDto);
                }
            }
        } catch (EncodeException | IOException ignored) {

        }
    }

    @OnClose
    public void close(final Session session) {

    }
}
