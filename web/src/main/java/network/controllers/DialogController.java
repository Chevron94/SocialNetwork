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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        model.addAttribute("user", user);
        List<UserDialog> userDialogs = userDialogService.getDialogsByUser(user);
        List<Dialog> dialogs = new ArrayList<>();
        for(UserDialog ud: userDialogs)
        {
            dialogs.add(ud.getDialog());
        }
        model.addAttribute("dialogs",dialogs);
        return "dialog";
    }

    @RequestMapping(value = "/dialog/getMessages", method = RequestMethod.GET)
    public @ResponseBody
    List<MessageDto> getMessages(@RequestParam(value="idDialog") Long idDialog, HttpServletRequest request)
    {
        Long userId = (Long)(request.getSession().getAttribute("idUser"));
        List<UserDialog> userDialogs = (List<UserDialog>)userDialogService.getDialogsByUser(userService.getUserById(userId));
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
            List<Message> messages = messageService.getMessagesByDialogId(Long.valueOf(idDialog));
            List<MessageDto> messagesDto = new ArrayList<>();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            for (Message message : messages) {
                MessageDto m = new MessageDto();
                m.setSender(userService.getUserById(message.getUser().getId()).getLogin());
                m.setMessageText(message.getText());
                m.setReceivedDate(message.getDateTime());
                m.setReceived(simpleDateFormat.format(message.getDateTime()));
                messagesDto.add(m);
            }
            return messagesDto;
        }
        else return null;
    }
}
