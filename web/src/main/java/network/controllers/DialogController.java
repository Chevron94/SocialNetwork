package network.controllers;

import network.dao.DialogDao;
import network.dao.MessageDao;
import network.dao.UserDao;
import network.dao.UserDialogDao;
import network.dto.MessageDto;
import network.entity.Dialog;
import network.entity.Message;
import network.entity.User;
import network.entity.UserDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roman on 10/4/15.
 */
@Controller
public class DialogController {
    @EJB
    UserDao userService;
    @EJB
    UserDialogDao userDialogService;
    @EJB
    DialogDao dialogService;
    @EJB
    MessageDao messageService;

    public void setUserService(UserDao userService) {
        this.userService = userService;
    }

    public void setUserDialogService(UserDialogDao userDialogService) {
        this.userDialogService = userDialogService;
    }

    public void setDialogService(DialogDao dialogService) {
        this.dialogService = dialogService;
    }

    public void setMessageService(MessageDao messageService) {
        this.messageService = messageService;
    }

    @RequestMapping(value = "/dialogs", method = RequestMethod.GET)
    public String getDialog(HttpServletRequest request, Model model)
    {
        Long idUser = (Long)request.getSession().getAttribute("idUser");
        User user=null;
        if (idUser == null)
        {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            user = userService.getUserByLogin(auth.getName());
            request.getSession().setAttribute("idUser",user.getId());
        }else
        {
            user = userService.getUserById(idUser);
        }
        idUser = user.getId();
        model.addAttribute("user", user);
        List<Dialog> dialogs = dialogService.getDialogsByUserId(idUser,0,10);
        for(Dialog dialog:dialogs){
            if(dialog.getPrivate()){
                List<UserDialog> userDialogs = new ArrayList<>();
                userDialogs.add(userDialogService.getUserDialogByPrivateDialogIdAndOtherUserId(dialog.getId(),idUser));
                dialog.setUserDialogs(userDialogs);
                String[] names = dialog.getName().split("/");
                if (names[0].trim().equals(user.getLogin())){
                    dialog.setName(names[1].trim());
                }else dialog.setName(names[0].trim());
            }
        }
        model.addAttribute("dialogs",dialogs);
        if (dialogs.size()>0){
            List<Message> messages = messageService.getMessagesByDialogId(dialogs.get(0).getId(),0,20);
            model.addAttribute("messages",messages);
        }
        return "dialog";
    }

    @RequestMapping(value = "/dialog/getMessages", method = RequestMethod.GET)
    public @ResponseBody
    List<MessageDto> getMessages(@RequestParam(value="idDialog") Long idDialog, @RequestParam(value = "startMessage") Integer startMessage, HttpServletRequest request)
    {
        Long userId = (Long)(request.getSession().getAttribute("idUser"));
        List<UserDialog> userDialogs = userDialogService.getDialogsByUser(userService.getUserById(userId), 0, Integer.MAX_VALUE);
        Dialog dialog = dialogService.getDialogById(idDialog);
        boolean ok = false;
        for(UserDialog d:userDialogs)
        {
            if(d.getDialog().equals(dialog))
            {
                ok=true;
                break;
            }
        }
        if (ok) {
            List<Message> messages = messageService.getMessagesByDialogId(Long.valueOf(idDialog), startMessage, 20);
            List<MessageDto> messagesDto = new ArrayList<>();
            for (Message message : messages) {
                MessageDto m = new MessageDto(message);
                messagesDto.add(m);
            }
            return messagesDto;
        }
        else return null;
    }

    @RequestMapping(value = "/dialogs/loadMore", method = RequestMethod.GET)
    public @ResponseBody List<Dialog> getDialogs(@RequestParam(value = "start") Integer start, HttpServletRequest request){
        Long idUser = (Long)(request.getSession().getAttribute("idUser"));
        List<Dialog> result = dialogService.getDialogsByUserId(idUser,start,10);
        for(Dialog dialog:result){
            if(dialog.getPrivate()){
                List<UserDialog> userDialogs = new ArrayList<>();
                userDialogs.add(userDialogService.getUserDialogByPrivateDialogIdAndOtherUserId(dialog.getId(),idUser));
                dialog.setUserDialogs(userDialogs);
                User user = userService.getUserById(idUser);
                String[] names = dialog.getName().split("/");
                if (names[0].trim().equals(user.getLogin())){
                    dialog.setName(names[1].trim());
                }else dialog.setName(names[0].trim());
            }
        }
        return result;
    }

    @RequestMapping(value = "/dialogs/{id}", method = RequestMethod.GET)
    public @ResponseBody Dialog getDialog(@PathVariable("id") Long idDialog, HttpServletRequest request){
        Long idUser = (Long)(request.getSession().getAttribute("idUser"));
        Dialog dialog = dialogService.getDialogById(idDialog);
        if(dialogService.getDialogsByUserId(idUser,0,Integer.MAX_VALUE).contains(dialog)){
            if(dialog.getPrivate()){
                List<UserDialog> userDialogs = new ArrayList<>();
                userDialogs.add(userDialogService.getUserDialogByPrivateDialogIdAndOtherUserId(dialog.getId(),idUser));
                dialog.setUserDialogs(userDialogs);
                User user = userService.getUserById(idUser);
                String[] names = dialog.getName().split("/");
                if (names[0].trim().equals(user.getLogin())){
                    dialog.setName(names[1].trim());
                }else dialog.setName(names[0].trim());
            }
        }
        return dialog;
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public @ResponseBody Boolean getOrCreateDialog(@RequestParam(value = "idSender") Long idSender, @RequestParam(value = "idReceiver") Long idReceiver,@RequestParam(value = "text") String text, HttpServletRequest request){
        Long userId = (Long)(request.getSession().getAttribute("idUser"));
        if(userId==idSender  && idReceiver!=idSender){
            User sender = userService.getUserById(idSender);
            User receiver = userService.getUserById(idReceiver);
            Dialog dialog = dialogService.getDialogByTwoUser(idSender,idReceiver);
            if(dialog==null){
                dialog = new Dialog(sender.getLogin()+" / "+receiver.getLogin(), true);
                dialog = dialogService.create(dialog);
                UserDialog senderUserDialog = new UserDialog(dialog,sender);
                UserDialog receiverUserDialog = new UserDialog(dialog,receiver);
                userDialogService.create(receiverUserDialog);
                userDialogService.create(senderUserDialog);
            }
            Message message = new Message(sender,dialog,text,new Date(),false);
            message = messageService.create(message);
            dialog.setLastMessageDate(message.getDateTime());
            dialogService.update(dialog);
            return true;
        }else return false;
    }

    @RequestMapping(value = "/unreadMessages", method = RequestMethod.GET)
    public @ResponseBody
    List<HashMap.Entry<Long,Long>> getDialogsWithCntUnreadMessages(@RequestParam(value = "idUser") Long idUser){
        List<HashMap.Entry<Long,Long>> result = new ArrayList<>();
        List<Dialog> dialogs = dialogService.getDialogsWithUnreadMessagesByUserId(idUser);
        for(Dialog dialog:dialogs){
            result.add(new HashMap.SimpleEntry<Long, Long>(dialog.getId(),messageService.getCountUnreadMessagesByUserIdAndDialogId(idUser,dialog.getId())));
        }
        return result;
    }
}
