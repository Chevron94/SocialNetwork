package network.controllers;

import network.dao.*;
import network.entity.Album;
import network.entity.FriendRequest;
import network.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by roman on 10/6/15.
 */
@Controller
public class PeopleController extends GenericController {

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
    @EJB
    LanguageDao languageService;
    @EJB
    LanguageLevelDao languageLevelService;
    @EJB
    LanguageUserDao languageUserService;

    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public String friends(Model model, HttpServletRequest request) {
        Long idRequestUser = getUserId(request);
        model.addAttribute("continents", continentService.readAll());
        model.addAttribute("languages", languageService.readAll());
        model.addAttribute("idRequestUser", idRequestUser);
        return "friends";
    }

    @RequestMapping(value = "/user{id}/friends", method = RequestMethod.GET)
    public String usersFriends(Model model, HttpServletRequest request, @PathVariable String id) {
        model.addAttribute("continents", continentService.readAll());
        model.addAttribute("languages", languageService.readAll());
        model.addAttribute("idRequestUser", Long.valueOf(id));
        return "friends";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model, HttpServletRequest request) {
        Long idRequestUser = getUserId(request);
        model.addAttribute("continents", continentService.readAll());
        model.addAttribute("languages", languageService.readAll());
        model.addAttribute("idRequestUser", idRequestUser);
        return "users";
    }


    /**
     * КОНТРОЛЛЕРЫ ЗАПРОСОВ
     **/
    @RequestMapping(value = "/people/sendRequest", method = RequestMethod.POST)
    public
    @ResponseBody
    Boolean sendFriendRequest(
            @RequestParam(value = "idReceiver") Long idReceiver,
            HttpServletRequest request) {
        Long idUser = (Long) request.getSession().getAttribute("idUser");
        User sender = userService.getUserById(idUser);
        User receiver = userService.getUserById(idReceiver);
        FriendRequest friendRequest = new FriendRequest(sender, receiver, false);
        FriendRequest repeat = friendRequestService.getFriendRequestByTwoUsersId(idReceiver, idUser);
        if (repeat == null) {
            friendRequestService.create(friendRequest);
            return false;
        } else {
            repeat.setConfirmed(true);
            friendRequestService.update(repeat);
            return true;
        }

    }

    @RequestMapping(value = "/people/confirmRequest", method = RequestMethod.POST)
    public
    @ResponseBody
    Boolean confirmRequest(
            @RequestParam(value = "idSender") Long idSender,
            HttpServletRequest request) {
        Long idUser = getUserId(request);
        FriendRequest friendRequest = friendRequestService.getFriendRequestByTwoUsersId(idSender, idUser);
        if (friendRequest == null) {
            return false;
        }
        friendRequest.setConfirmed(true);
        friendRequestService.update(friendRequest);
        return true;
    }

    @RequestMapping(value = "/people/deleteRequest", method = RequestMethod.POST)
    public
    @ResponseBody
    Boolean deleteRequest(
            @RequestParam(value = "idReceiver") Long idReceiver,
            HttpServletRequest request) {
        Long idUser = getUserId(request);
            FriendRequest friendRequest = friendRequestService.getFriendRequestBySenderAndReceiverId(idUser, idReceiver);
            if (friendRequest == null) {
                return false;
            }
            friendRequestService.delete(friendRequest.getId());
            return true;
    }

    @RequestMapping(value = "/people/more", method = RequestMethod.GET)
    public
    @ResponseBody
    List<User> loadMoreFriends(
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
            @RequestParam(value = "idLanguage[]") Long[] idLanguage,
            @RequestParam(value = "list") String list,
            @RequestParam(value = "start") Integer start
    ) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("login", login);
        params.put("idContinent", idContinent);
        params.put("idCountry", idCountry);
        params.put("idCity", idCity);
        params.put("male", male);
        params.put("female", female);
        params.put("ageFrom", ageFrom);
        params.put("ageTo", ageTo);
        params.put("idLanguage", idLanguage);
        params.put("list", list);
        Long idUser = getUserId(request);
        if ((list.equals("sent") || list.equals("received")) && !Objects.equals(idRequestUser, idUser)) {
            return null;
        }
        List<User> result = userService.getUsersByCustomFilter(idRequestUser, params, start, 20);
        for (User user : result) {
            Album album = albumService.getAlbumByUserIdAndName(user.getId(), "Main");
            album.setPhotos(photoService.getPhotosByAlbumId(album.getId(), 0, 1));
            List<Album> albums = new ArrayList<>();
            albums.add(album);
            user.setAlbums(albums);
            user.setLanguageUsers(languageUserService.getLanguagesByUser(user, 0, Integer.MAX_VALUE));
        }
        return result;
    }

    @RequestMapping(value = "/people/requests", method = RequestMethod.GET)
    public
    @ResponseBody
    Long getCountOfFriendRequests(HttpServletRequest request) {
        Long idUser = getUserId(request);
        if (idUser == null)
            return null;
        return friendRequestService.getNumberOfFriendRequests(idUser, false);
    }
}
