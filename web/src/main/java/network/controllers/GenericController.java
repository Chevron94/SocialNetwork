package network.controllers;

import network.dao.UserDao;
import network.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Роман on 02.10.2016.
 */
@Controller
public class GenericController {
    @EJB
    UserDao userService;

    public Long getUserId(HttpServletRequest request) {
        Long idUser = (Long) request.getSession().getAttribute("idUser");
        if (idUser == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByLogin(auth.getName());
            request.getSession().setAttribute("idUser", user.getId());
            idUser = user.getId();
        }
        return idUser;
    }
}
