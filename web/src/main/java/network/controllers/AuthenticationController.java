package network.controllers;

import network.dao.UserDao;
import network.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import javax.ejb.EJB;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Роман on 19.03.2016.
 */
@Controller
public class AuthenticationController {

    @EJB
    UserDao userService;

    public boolean authenticate(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("REMEMBER_ME")) {
                    String result = cookie.getValue();
                    User user = userService.getUserByToken(result, false);
                    if (user != null) {
                        Authentication auth = new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword(), getGrantedAuthorities(getRoles(user.getRole())));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        user.setToken(UUID.randomUUID().toString());
                        userService.update(user);
                        response.addCookie(new Cookie("REMEMBER_ME",user.getToken()));
                        return true;
                    } else return false;
                } else return false;
            }
        }return false;
    }

    public List getRoles(int role)
    {
        List roles = new ArrayList();
        if (role==1)
        {
            roles.add("ROLE_ADMIN");
            roles.add("ROLE_USER");
        }else roles.add("ROLE_USER");
        return roles;
    }

    public static List getGrantedAuthorities(List roles) {
        List authorities = new ArrayList();
        for (Object role : roles) {
            authorities.add(new SimpleGrantedAuthority(String.valueOf(role)));
        }
        return authorities;
    }
}
