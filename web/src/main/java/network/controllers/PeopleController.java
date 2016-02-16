package network.controllers;

import network.dao.*;
import network.dto.SearchDto;
import network.entity.FriendRequest;
import network.entity.User;
import network.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
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
        List<FriendRequest> friendRequestsList = friendRequestService.getFriendsByUserId(idRequestUser);
        List<User> friends = new ArrayList<>();
        for(FriendRequest fr:friendRequestsList)
        {
            User sender = fr.getSender();
            if(sender.getId() != idRequestUser)
                friends.add(sender);
            else friends.add(fr.getReceiver());
        }
        model.addAttribute("friends",friends);
        model.addAttribute("continents",continentService.readAll());
        if (idRequestUser == idUser) {

            List<FriendRequest> receivedFriendRequestsList = friendRequestService.getFriendRequestsByReceiverId(idRequestUser);
            List<FriendRequest> sendedFriendRequestsList = friendRequestService.getFriendRequestsBySenderId(idRequestUser);
            List<User> sendedRequests = new ArrayList<>();
            List<User> receivedRequests = new ArrayList<>();

            for (FriendRequest fr : receivedFriendRequestsList) {
                receivedRequests.add(fr.getSender());
            }

            for (FriendRequest fr : sendedFriendRequestsList) {
                sendedRequests.add(fr.getReceiver());
            }
            model.addAttribute("received_friends_requests",receivedRequests);
            model.addAttribute("sended_friends_requests",sendedRequests);
            /*List<User> others = userService.readAll();
            for(User u:friends)
                others.remove(u);
            for(User u:receivedRequests)
                others.remove(u);
            for(User u:sendedRequests)
                others.remove(u);
            others.remove(userService.getUserById(idUser));
            model.addAttribute("users",others); */
        }
        return "people";
    }

    @RequestMapping(value = "/user{id}/friends", method = RequestMethod.POST)
    public String searchPeople(@ModelAttribute("searchDto") @Valid SearchDto searchDto,
                               BindingResult bindingResult,
                               ModelMap modelMap,
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

        List<User> users = userService.getUsersByCustomFilter(idContinent, idCountry, idCity, male,female,ageFrom,ageTo,idLanguage);

        List<FriendRequest> friendRequestsList = friendRequestService.getFriendsByUserId(idRequestUser);
        List<User> friends = new ArrayList<>();
        for(FriendRequest fr:friendRequestsList)
        {
            User sender = fr.getSender();
            if(sender.getId() != idRequestUser)
            {
                if(users.contains(sender))
                {
                    friends.add(sender);
                    users.remove(sender);
                }
            }
            else if(users.contains(fr.getReceiver()))
            {
                friends.add(fr.getReceiver());
                users.remove(fr.getReceiver());
            }
        }

        model.addAttribute("friends",friends);
        model.addAttribute("continents", continentService.readAll());
            if (idContinent>0) {
                model.addAttribute("countries", countryService.getCountryByContinentId(idContinent));
                if (idCountry>0)
                    model.addAttribute("cities", cityService.getCitiesByCountryId(idCountry));
            }

        model.addAttribute("searchDto", searchDto);
        if (idRequestUser == idUser) {

            List<FriendRequest> receivedFriendRequestsList = friendRequestService.getFriendRequestsByReceiverId(idRequestUser);
            List<FriendRequest> sendedFriendRequestsList = friendRequestService.getFriendRequestsBySenderId(idRequestUser);
            List<User> sendedRequests = new ArrayList<>();
            List<User> receivedRequests = new ArrayList<>();

            for (FriendRequest fr : receivedFriendRequestsList) {
                if(users.contains(fr.getSender()))
                {
                    receivedRequests.add(fr.getSender());
                    users.remove(fr.getSender());
                }
            }

            for (FriendRequest fr : sendedFriendRequestsList) {
                if(users.contains(fr.getReceiver()))
                {
                    sendedRequests.add(fr.getReceiver());
                    users.remove(fr.getReceiver());
                }
            }

            User curUser = userService.getUserById(idRequestUser);
            if (users.contains(curUser)) users.remove(curUser);
            model.addAttribute("received_friends_requests",receivedRequests);
            model.addAttribute("sended_friends_requests",sendedRequests);
            model.addAttribute("users", users);
        }
        return "people";
    }


    /**            КОНТРОЛЛЕРЫ ЗАПРОСОВ                  **/
    @RequestMapping(value = "/people/sendRequest", method = RequestMethod.GET)
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

    @RequestMapping(value = "/people/confirmRequest", method = RequestMethod.GET)
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

    @RequestMapping(value = "/people/deleteRequest", method = RequestMethod.GET)
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
}
