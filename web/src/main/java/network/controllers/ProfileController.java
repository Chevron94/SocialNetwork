package network.controllers;

import network.dao.FriendRequestDao;
import network.dao.UserDao;
import network.dao.implementation.UserDaoImplementation;
import network.entity.FriendRequest;
import network.entity.User;
import network.service.FriendRequestService;
import network.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 10/4/15.
 */
@Controller
public class ProfileController {
    @EJB
    UserDao userService;
    @EJB
    FriendRequestDao friendRequestService;

    public void setUserService(UserDao userService) {
        this.userService = userService;
    }

    public void setFriendRequestService(FriendRequestDao friendRequestService) {
        this.friendRequestService = friendRequestService;
    }
    

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getMainProfile(Model model, HttpServletRequest request) {
        Long idUser = (Long)request.getSession().getAttribute("idUser");
        if (idUser == null)
        {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByLogin(auth.getName());
            request.getSession().setAttribute("idUser",idUser);
            if (user!=null) {
                idUser = user.getId();
            }else return "redirect:/login";
        }
        return "redirect:/user"+idUser;
    }


    @RequestMapping(value = "/user{id}", method = RequestMethod.GET)
    public String getUserProfile(Model model, HttpServletRequest request, @PathVariable String id) {

        Long idUser = (Long)request.getSession().getAttribute("idUser");
        if (idUser == null)
        {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User loginUser = userService.getUserByLogin(auth.getName());
            request.getSession().setAttribute("idUser",loginUser.getId());
        }
        User user = userService.getUserById(Long.valueOf(id));

        List<FriendRequest> friendRequests = friendRequestService.getFriendsByUserId(user.getId());
        List<Long> ids=new ArrayList<Long>();
        for(FriendRequest req:friendRequests)
        {
            if (req.getSender().getId() != user.getId())
                ids.add(req.getSender().getId());
            if (req.getReceiver().getId() != user.getId())
                ids.add(req.getReceiver().getId());
        }
        List<User> users = new ArrayList<User>();
        for(Long userId:ids)
        {
            users.add(userService.getUserById(userId));
        }
        model.addAttribute("friends",users);
        model.addAttribute("user",user);
        return "profile";
    }
}
