package network.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import network.dao.*;
import network.dto.LanguageDto;
import network.dto.UserDto;
import network.entity.Album;
import network.entity.FriendRequest;
import network.entity.Photo;
import network.entity.User;
import network.services.MD5Service;
import org.apache.commons.io.IOUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by roman on 10/4/15.
 */
@Controller
public class ProfileController {
    @EJB
    UserDao userService;
    @EJB
    FriendRequestDao friendRequestService;
    @EJB
    PhotoDao photoService;
    @EJB
    AlbumDao albumService;
    @EJB
    LanguageUserDao languageUserService;
    @EJB
    LanguageLevelDao languageLevelService;
    @EJB
    LanguageDao languageService;
    @EJB
    GenderDao genderService;
    @EJB
    CountryDao countryService;
    @EJB
    CityDao cityService;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getMainProfile(Model model, HttpServletRequest request) {
        Long idUser = (Long)request.getSession().getAttribute("idUser");
        if (idUser == null)
        {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByLogin(auth.getName());
            if (user!=null) {
                idUser = user.getId();
                request.getSession().setAttribute("idUser",idUser);
            }else return "redirect:/login";
        }
        return "forward:/user"+idUser;
    }

    @RequestMapping(value = "/profile/updatePhoto", method = RequestMethod.POST)
    public String updatePhoto(@RequestParam("photoInput") MultipartFile file, HttpServletRequest request){
        Long idUser = (Long)request.getSession().getAttribute("idUser");
        if (!file.isEmpty()) {
            Map options = ObjectUtils.asMap(
                    "cloud_name", "chevron",
                    "api_key", "955731587757792",
                    "api_secret", "_An7669JFG-iRVrBjEdxQ1iwnDY");
            Map uploadResult;
            Cloudinary cloudinary = new Cloudinary(options);
            try {
                uploadResult = cloudinary.uploader().upload(stream2file(file.getInputStream()), options);
                Album album = albumService.getAlbumByUserIdAndName(idUser,"Main");
                User user = userService.getUserById(idUser);
                if(album == null){
                    album = new Album("Main",user, new Date());
                    album = albumService.create(album);
                }
                Photo photo = new Photo((String) uploadResult.get("secure_url"),new java.util.Date(), album);
                photoService.create(photo);
                user.setPhotoURL((String) uploadResult.get("secure_url"));
                userService.update(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/profile";
    }

    public File stream2file (InputStream in) throws IOException {
        final File tempFile = File.createTempFile("stream2file", ".tmp");
        tempFile.deleteOnExit();
        FileOutputStream out = new FileOutputStream(tempFile);
        IOUtils.copy(in, out);
        return tempFile;
    }


    @RequestMapping(value = "/user{id}", method = RequestMethod.GET)
    public String getUserProfile(Model model, HttpServletRequest request, @PathVariable String id) {

        Long idUser = (Long)request.getSession().getAttribute("idUser");
        if (idUser == null)
        {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User loginUser = userService.getUserByLogin(auth.getName());
            request.getSession().setAttribute("idUser",loginUser.getId());
            idUser = loginUser.getId();
        }
        User user = userService.getUserById(Long.valueOf(id));
        if (user != null) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("list","friends");
            List<User> users = userService.getUsersByCustomFilter(user.getId(),params,0,9);
            Album album = albumService.getAlbumByUserIdAndName(user.getId(),"Main");
            album.setPhotos(photoService.getPhotosByAlbumId(album.getId(),0,1));
            List<Album> albums = new ArrayList<>();
            albums.add(album);
            user.setAlbums(albums);
            user.setLanguageUsers(languageUserService.getLanguagesByUser(user,0,Integer.MAX_VALUE));
            model.addAttribute("friends", users);
            model.addAttribute("user", user);
            Long idRequestUser=Long.valueOf(id);
            if(idRequestUser!=idUser){
                FriendRequest friendRequest = friendRequestService.getFriendRequestByTwoUsersId(idUser,idRequestUser);
                model.addAttribute("friendRequest",friendRequest);
            }
            model.addAttribute("languages", languageService.readAll());
            model.addAttribute("languageLevels", languageLevelService.readAll());
            model.addAttribute("countries", countryService.readAll());
            model.addAttribute("genders", genderService.readAll());
            return "profile";
        }return "forward:/404";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String updateProfile(Model model, @ModelAttribute("editForm") UserDto userDto,  HttpServletRequest request){
        Long idUser = (Long)request.getSession().getAttribute("idUser");
        User user = userService.getUserById(idUser);
        if(userDto.getLogin().equals(user.getLogin()) || userService.getUserByLogin(userDto.getLogin()) == null){
            if(userDto.getOldpassword() == null || userDto.getOldpassword().trim().length()==0 || MD5Service.md5(userDto.getOldpassword()).equals(user.getPassword())){
                if (userDto.getOldpassword() != null && MD5Service.md5(userDto.getOldpassword()).equals(user.getPassword()) && userDto.getPassword().length()>0){
                    user.setPassword(MD5Service.md5(userDto.getPassword()));
                }
                user.setLogin(userDto.getLogin());
                user.setName(userDto.getName());
                user.setBirthday(java.sql.Date.valueOf(userDto.getBirthday()));
                if (userDto.getCity() != null){
                    user.setCity(cityService.getCityById(userDto.getCity()));
                }
                user.setCountry(countryService.getCountryById(userDto.getCountry()));
                user.setGender(genderService.getGenderById(userDto.getGender()));
                user.setEmail(userDto.getEmail());
                user.setDescription(userDto.getDescription());
                user = userService.update(user);
            }else {
                model.addAttribute("error","Incorrect password");
            }
        }else {
            model.addAttribute("error","Login already used");
        }
        return getUserProfile(model,request,user.getId().toString());
    }

    @RequestMapping(value="/profile/updateMainPhoto", method = RequestMethod.POST)
    public @ResponseBody Boolean updateMainPhoto(@RequestParam("idUser") Long idRequestUser,
                                                 @RequestParam("idPhoto") Long idPhoto,
                                                 HttpServletRequest request){
        Long idUser = (Long)request.getSession().getAttribute("idUser");
        if (idRequestUser == idUser){
            Album album = albumService.getAlbumByUserIdAndName(idRequestUser,"Main");
            Photo photo = photoService.getPhotoByID(idPhoto);
            User user = userService.getUserById(idRequestUser);
            if(user.getPhotoURL().equals(photo.getPhotoUrl())){
                return false;
            }
            user.setPhotoURL(photo.getPhotoUrl());
            if (!photo.getAlbum().equals(album)) {
                Photo mainPhoto = new Photo(photo.getPhotoUrl(), new Date(), album);
                photoService.create(mainPhoto);
            }else {
                photo.setUploaded(new Date());
                photoService.update(photo);
            }
            userService.update(user);
            return true;
        }return null;
    }
}
