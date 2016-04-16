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
import network.service.events.MessageEvent;
import network.service.events.NewMessageEvent;
import network.service.events.ReadEvent;
import network.service.events.ReadMessageEvent;

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
@ServerEndpoint(value = "/dialog", encoders = {DialogMessageEncoder.class}, decoders = {DialogMessageDecoder.class})
@Stateful
public class DialogEndpoint {
    private final Logger log = Logger.getLogger(getClass().getName());
    private static Map<User,Session> peers = Collections.synchronizedMap(new HashMap<User, Session>());

    @EJB
    DialogDao dialogService;
    @EJB
    UserDao userService;
    @EJB
    UserDialogDao userDialogService;
    @EJB
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
                peers.put(userService.getUserById(Long.valueOf(chatMessage.getId())),session);
            } else {
                if (chatMessage.getMessageText() != null && chatMessage.getMessageText().length()>0) {
                    Dialog d = dialogService.getDialogById(Long.valueOf(chatMessage.getReceiverDialog()));
                    messageService.create(new Message(userService.getUserById(Long.valueOf(chatMessage.getSenderId())), d, chatMessage.getMessageText(), chatMessage.getReceivedDate(), false));
                    d.setLastMessageDate(chatMessage.getReceivedDate());
                    d = dialogService.update(d);
                }else{
                    messageService.readMessages(Long.valueOf(chatMessage.getSenderId()), Long.valueOf(chatMessage.getReceiverDialog()));
                }
              /*  List<UserDialog> userDialogs = userDialogService.getUsersByDialog(d);
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
                }*/
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
                if (peers.get(ud.getUser()) != null) {
                    sessions.add(peers.get(ud.getUser()));
                }
            }
            for (Session s : sessions) {
                if (s.isOpen()) {
                    s.getBasicRemote().sendObject(messageDto);
                }
            }
        } catch (EncodeException e) {

        } catch (IOException e) {

        }
    }

    public void readEvent(@Observes @ReadMessageEvent ReadEvent readEvent){
        try {
            MessageDto messageDto = new MessageDto();
            messageDto.setSenderId(readEvent.getIdUser().toString());
            messageDto.setReceiverDialog(readEvent.getIdDialog().toString());
            messageDto.setMessageText("");
            Dialog d = dialogService.getDialogById(readEvent.getIdDialog());
            List<UserDialog> userDialogs = userDialogService.getUsersByDialog(d);
            List<Session> sessions = new ArrayList<>();
            for (UserDialog ud : userDialogs) {
                if (peers.get(ud.getUser()) != null) {
                    sessions.add(peers.get(ud.getUser()));
                }
            }
            for (Session s : sessions) {
                if (s.isOpen()) {
                    s.getBasicRemote().sendObject(messageDto);
                }
            }
        } catch (EncodeException e) {

        } catch (IOException e) {

        }
    }

    @OnClose
    public void close(final Session session) {
        peers.remove(session);
    }
}
