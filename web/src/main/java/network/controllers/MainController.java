package network.controllers;

import network.dao.CityDao;
import network.dao.CountryDao;
import network.dao.GenderDao;
import network.dao.UserDao;
import network.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.ejb.EJB;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by roman on 9/26/15.
 */
@Controller
public class MainController{

    @EJB
    UserDao userService;
    @EJB
    CityDao cityService;
    @EJB
    GenderDao genderService;
    @EJB
    CountryDao countryService;

    @Autowired
    AuthenticationController authenticationController;

    public void setUserService(UserDao userService) {
        this.userService = userService;
    }

    public void setCityService(CityDao cityService) {
        this.cityService = cityService;
    }

    public void setGenderService(GenderDao genderService) {
        this.genderService = genderService;
    }

    public void setCountryService(CountryDao countryService) {
        this.countryService = countryService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root(Model model, HttpServletRequest request, HttpServletResponse response) {
        Long idUser = (Long)request.getSession().getAttribute("idUser");
        if (idUser == null)
        {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User loginUser = userService.getUserByLogin(auth.getName());
            if (loginUser == null){
                return "redirect:/login";
            }
            idUser = loginUser.getId();
            request.getSession().setAttribute("idUser",idUser);
        }
        return "redirect:/user"+idUser;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
       //TODO REMEMBER ME
        /*if (authenticationController.authenticate(request,response))
        {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User loginUser = userService.getUserByLogin(auth.getName());
            Long idUser = loginUser.getId();
            request.getSession().setAttribute("idUser",idUser);
            response.sendRedirect(request.getHeader("referer")==null ? "/" : request.getHeader("referer"));
        }*/
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        String success = (String)request.getSession().getAttribute("msg");
        if (success != null) {
            model.addObject("msg", success);
            request.getSession().removeAttribute("msg");
        }
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/admin**", method = RequestMethod.GET)
    public ModelAndView adminPage() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Hello World");
        model.addObject("message", "This is protected page!");
        model.setViewName("admin");

        return model;
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String error404() {
        return "404";
    }

}
