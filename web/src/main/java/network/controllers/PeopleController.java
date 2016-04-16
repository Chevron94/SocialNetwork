package network.controllers;

import network.dao.*;
import network.dto.SearchDto;
import network.entity.Album;
import network.entity.FriendRequest;
import network.entity.User;
import org.springframework.security.authentication.dao.SystemWideSaltSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roman on 10/6/15.
 */
@Controller
public class PeopleController {

    @EJB
    UserDao userService;
    @EJB
    CityDao cityService;
    @EJB
    GenderDao genderService;
    @EJB
    FriendRequestDao friendRequestService;
    @EJB
    CountryDao countryService;
    @EJB
    ContinentDao continentService;
    @EJB
    PhotoDao photoService;
    @EJB
    AlbumDao albumService;


    public void setUserService(UserDao userService) {
        this.userService = userService;
    }

    public void setCityService(CityDao cityService) {
        this.cityService = cityService;
    }

    public void setGenderService(GenderDao genderService) {
        this.genderService = genderService;
    }

    public void setFriendRequestService(FriendRequestDao friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    public void setCountryService(CountryDao countryService) {
        this.countryService = countryService;
    }

    public void setContinentService(ContinentDao continentService) {
        this.continentService = continentService;
    }

    public Long getUserId(HttpServletRequest request){
        Long idUser = (Long)request.getSession().getAttribute("idUser");
        if(idUser==null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByLogin(auth.getName());
            request.getSession().setAttribute("idUser", user.getId());
            idUser = user.getId();
        }
        return idUser;
    }

    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public String friends(Model model, HttpServletRequest request){
        Long idRequestUser = getUserId(request);
        model.addAttribute("continents",continentService.readAll());
        model.addAttribute("idRequestUser", idRequestUser);
        return "friends";
    }

    @RequestMapping(value = "/user{id}/friends", method = RequestMethod.GET)
    public String usersFriends(Model model, HttpServletRequest request, @PathVariable String id){
        Long idUser = getUserId(request);
        model.addAttribute("continents",continentService.readAll());
        model.addAttribute("idRequestUser", Long.valueOf(id));
        return "friends";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model, HttpServletRequest request){
        Long idRequestUser = getUserId(request);
        model.addAttribute("continents",continentService.readAll());
        model.addAttribute("idRequestUser", idRequestUser);
        return "users";
    }



    /**            КОНТРОЛЛЕРЫ ЗАПРОСОВ                  **/
    @RequestMapping(value = "/people/sendRequest", method = RequestMethod.POST)
    public @ResponseBody
    Boolean sendFriendRequest(
            @RequestParam(value="idSender") Long idSender,
            @RequestParam(value = "idReceiver") Long idReceiver,
            HttpServletRequest request) {
        Long idUser = (Long)request.getSession().getAttribute("idUser");
        if (idUser == idSender){
            User sender = userService.getUserById(idSender);
            User receiver = userService.getUserById(idReceiver);
            FriendRequest friendRequest = new FriendRequest(sender, receiver, false);
            FriendRequest repeat = friendRequestService.getFriendRequestByTwoUsersId(idReceiver, idSender);
            if (repeat == null) {
                friendRequestService.create(friendRequest);
                return false;
            }else {
                repeat.setConfirmed(true);
                friendRequestService.update(repeat);
                return true;
            }
        }return null;
    }

    @RequestMapping(value = "/people/confirmRequest", method = RequestMethod.POST)
    public @ResponseBody
    Boolean confirmRequest(
            @RequestParam(value="idSender") Long idSender,
            @RequestParam(value = "idReceiver") Long idReceiver,
            HttpServletRequest request) {
        Long idUser = (Long)request.getSession().getAttribute("idUser");
        if (idUser == idReceiver) {
            FriendRequest friendRequest = friendRequestService.getFriendRequestBySenderAndReceiverId(idSender, idReceiver);
            if (friendRequest == null) {
                return false;
            }
            friendRequest.setConfirmed(true);
            friendRequestService.update(friendRequest);
            return true;
        }return null;
    }

    @RequestMapping(value = "/people/deleteRequest", method = RequestMethod.POST)
    public @ResponseBody
    Boolean deleteRequest(
            @RequestParam(value="idSender") Long idSender,
            @RequestParam(value = "idReceiver") Long idReceiver,
            HttpServletRequest request) {
        Long idUser = (Long)request.getSession().getAttribute("idUser");
        if (idUser == idReceiver || idUser == idSender) {
            FriendRequest friendRequest = friendRequestService.getFriendRequestBySenderAndReceiverId(idSender, idReceiver);
            if (friendRequest == null) {
                return false;
            }
            friendRequestService.delete(friendRequest.getId());
            return true;
        }return null;
    }

    @RequestMapping(value = "/people/more", method = RequestMethod.GET)
    public @ResponseBody List<User> loadMoreFriends(
            HttpServletRequest request,
            @RequestParam(value = "idUser") Long idRequestUser,
            @RequestParam(value = "login") String login,
            @RequestParam(value = "idContinent") Long idContinent,
            @RequestParam(value = "idCountry") Long idCountry,
            @RequestParam(value = "idCity") Long idCity,
            @RequestParam(value = "male") Boolean male,
            @RequestParam(value = "female") Boolean female,
            @RequestParam(value = "ageFrom") Integer ageFrom,
            @RequestParam(value = "ageTo") Integer ageTo,
            @RequestParam(value = "idLanguage") Long idLanguage,
            @RequestParam(value = "list") String list,
            @RequestParam(value = "start") Integer start
            ){
        HashMap<String, Object> params = new HashMap<>();
        params.put("login", login);
        params.put("idContinent", idContinent);
        params.put("idCountry", idCountry);
        params.put("idCity", idCity);
        params.put("male",male);
        params.put("female", female);
        params.put("ageFrom", ageFrom);
        params.put("ageTo",ageTo);
        params.put("idLanguage", idLanguage);
        params.put("list",list);
        Long idUser = (Long)request.getSession().getAttribute("idUser");
        if((list.equals("sent") || list.equals("received")) && idRequestUser != idUser)
            return null;
        List<User> result = userService.getUsersByCustomFilter(idRequestUser,params,start,20);
        for(User user:result){
            Album album = albumService.getAlbumByUserIdAndName(user.getId(),"Main");
            album.setPhotos(photoService.getPhotosByAlbumId(album.getId(),0,1));
            List<Album> albums = new ArrayList<>();
            albums.add(album);
            user.setAlbums(albums);
        }
        return result;
    }
}
