package network.controllers;

import network.dao.*;
import network.dto.SearchDto;
import network.entity.FriendRequest;
import network.entity.User;
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

    @RequestMapping(value = "/user{id}/friends", method = RequestMethod.GET)
    public String getPeople(Model model, HttpServletRequest request, @PathVariable String id)
    {
        Long idRequestUser = Long.parseLong(id);
        Long idUser = (Long)request.getSession().getAttribute("idUser");
        if (idUser == null)
        {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByLogin(auth.getName());
            idUser = user.getId();
            request.getSession().setAttribute("idUser",user.getId());
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("idContinent", 0L);
        params.put("idCountry", 0L);
        params.put("idCity", 0L);
        params.put("male",true);
        params.put("female", true);
        params.put("ageFrom", 0);
        params.put("ageTo", 100);
        params.put("idLanguage", 0L);
        params.put("list","friends");

        List<User> friends = userService.getUsersByCustomFilter(idRequestUser,params, 0, 20);
        model.addAttribute("friends",friends);
        model.addAttribute("continents",continentService.readAll());
        if (idRequestUser == idUser) {

            params.put("list","received");
            List<User> receivedRequests = userService.getUsersByCustomFilter(idRequestUser,params,0,20);
            params.put("list","sent");
            List<User> sentRequests = userService.getUsersByCustomFilter(idRequestUser,params, 0, 20);
            model.addAttribute("receivedFriendsRequests",receivedRequests);
            model.addAttribute("sentFriendsRequests",sentRequests);
            model.addAttribute("idRequestUser", idRequestUser);
        }
        return "friends";
    }

    @RequestMapping(value = "/user{id}/friends", method = RequestMethod.POST)
    public String searchPeople(@ModelAttribute("searchDto") @Valid SearchDto searchDto,
                               Model model, HttpServletRequest request, @PathVariable String id)
    {
        Long idRequestUser = Long.parseLong(id);
        Long idUser = (Long)request.getSession().getAttribute("idUser");
        if (idUser == null)
        {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByLogin(auth.getName());
            idUser = user.getId();
            request.getSession().setAttribute("idUser",user.getId());
        }

        Long idContinent = Long.parseLong(searchDto.getContinent());
        Long idCountry = Long.parseLong(searchDto.getCountry());
        Long idCity = Long.parseLong(searchDto.getCity());
        boolean male = searchDto.getGender().contains("m");
        boolean female = searchDto.getGender().contains("f");
        Long idLanguage = Long.parseLong(searchDto.getLanguage());
        int ageFrom = Integer.parseInt(searchDto.getAgeFrom());
        int ageTo = Integer.parseInt(searchDto.getAgeTo());

        if (male == female)
        {
            male = true;
            female = true;
        }

        HashMap<String, Object> params = new HashMap<>();
        params.put("idContinent", idContinent);
        params.put("idCountry", idCountry);
        params.put("idCity", idCity);
        params.put("male",male);
        params.put("female", female);
        params.put("ageFrom", ageFrom);
        params.put("ageTo",ageTo);
        params.put("idLanguage", idLanguage);
        params.put("list","friends");

        List<User> friends = userService.getUsersByCustomFilter(idRequestUser,params, 0, 20);

        model.addAttribute("friends",friends);
        model.addAttribute("continents", continentService.readAll());
            if (idContinent>0) {
                model.addAttribute("countries", countryService.getCountryByContinentId(idContinent));
                if (idCountry>0 && idCity>0)
                    model.addAttribute("city", cityService.getCityById(idCity));
            }

        model.addAttribute("searchDto", searchDto);
        if (idRequestUser == idUser) {

            params.put("list","received");
            List<User> receivedRequests = userService.getUsersByCustomFilter(idRequestUser,params,0,20);
            params.put("list","sent");
            List<User> sentRequests = userService.getUsersByCustomFilter(idRequestUser,params, 0, 20);
            model.addAttribute("receivedFriendsRequests",receivedRequests);
            model.addAttribute("sentFriendsRequests",sentRequests);
            model.addAttribute("idRequestUser", idRequestUser);
        }
        return "friends";
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
            FriendRequest repeat = friendRequestService.getFriendRequestBySenderAndReceiverId(idReceiver, idSender);
            if (repeat == null) {
                if (friendRequestService.getFriendRequestBySenderAndReceiverId(idSender, idReceiver) == null) {
                    friendRequestService.create(friendRequest);
                }
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
            @RequestParam(value = "idUser") Long idUser,
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
        params.put("idContinent", idContinent);
        params.put("idCountry", idCountry);
        params.put("idCity", idCity);
        params.put("male",male);
        params.put("female", female);
        params.put("ageFrom", ageFrom);
        params.put("ageTo",ageTo);
        params.put("idLanguage", idLanguage);
        params.put("list",list);
        return userService.getUsersByCustomFilter(idUser,params,start,20);
    }
}
