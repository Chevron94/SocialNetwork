package network.controllers;

import network.dao.*;
import network.dto.UserDto;
import network.entity.*;
import network.service.*;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import network.services.SHAService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by roman on 10/7/15.
 */
@Controller
public class RegistrationController {
    @EJB
    UserDao userService;
    @EJB
    CityDao cityService;
    @EJB
    GenderDao genderService;
    @EJB
    CountryDao countryService;
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

    public void setCountryService(CountryDao countryService) {
        this.countryService = countryService;
    }

    public void setPhotoService(PhotoDao photoService) {
        this.photoService = photoService;
    }

    public void setAlbumService(AlbumDao albumService) {
        this.albumService = albumService;
    }

    @RequestMapping(value = "/registration/citiesByCountry", method = RequestMethod.GET)
    public @ResponseBody
    List<City> getCities(
            @RequestParam(value="searchId") Long searchId) {
        List<City> cities = cityService.getCitiesByCountryId(searchId);

        return cityService.getCitiesByCountryId(searchId);
    }

    @RequestMapping(value = "/registration/countriesByContinent", method = RequestMethod.GET)
    public @ResponseBody
    List<Country> getCountries(
            @RequestParam(value="searchId") Long searchId) {
        List<Country> countries = countryService.getCountryByContinentId(searchId);

        return countries;
    }

    @RequestMapping(value = "/registration/checkLogin", method = RequestMethod.GET)
    public @ResponseBody
    Boolean checkUserLogin(
            @RequestParam(value="login") String login) {
        return (userService.getUserByLogin(login)!=null);
    }

    @RequestMapping(value = "/registration/checkEmail", method = RequestMethod.GET)
    public @ResponseBody
    Boolean checkUserEmail(
            @RequestParam(value="email") String email) {
        return (userService.getUserByEmail(email)!=null);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String getRegistration(Model model) {

        //     CountryService countryService = new CountryService();
        List<Country> tmp = countryService.readAll();
        model.addAttribute("countries",countryService.readAll());
        model.addAttribute("genders",genderService.readAll());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String setRegistration(@ModelAttribute("user") @Valid UserDto user,
                                  BindingResult bindingResult,
                                  ModelMap modelMap,
                                  Model model,
                                  HttpServletRequest request,
                                  @RequestParam("filePhoto") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            List<String> validationErrors = new ArrayList<String>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                validationErrors.add(error.getDefaultMessage());
            }
        }
        List<String> validationErrors = new ArrayList<String>();
        if (userService.getUserByLogin(user.getLogin()) != null) {
            validationErrors.add("Login is already used");
        }

        Pattern p = Pattern.compile("^\\w+$");
        Pattern q = Pattern.compile("^\\w+([\\s-]\\w+)*$");
        Matcher m = p.matcher(user.getLogin());
        if (!m.matches()) {
            validationErrors.add("Login must be contains only latin symbols and digits");
        }
        m = q.matcher(user.getName());
        if (!m.matches()) {
            validationErrors.add("Name must be contains only latin symbols, digits, '-',_,or space");
        }
        Long countryId = Long.valueOf(user.getCountry());
        Long cityId = Long.valueOf(user.getCity());
        Long genderId = Long.valueOf(user.getGender());
        if (countryId == 0) {
            validationErrors.add("Please select country");
        }

        if (user.getBirthday().equals("")) {
            validationErrors.add("Please select date");
        }

        if (cityId == 0) {
            validationErrors.add("Please select city");
        }

        if (genderId == 0) {
            validationErrors.add("Please select gender");
        }

        if (validationErrors.size() > 0) {
            model.addAttribute("countries", countryService.readAll());
            model.addAttribute("genders", genderService.readAll());
            model.addAttribute("user", user);
            model.addAttribute("errors", validationErrors);
            if (countryId != 0)
                model.addAttribute("cities", cityService.getCitiesByCountryId(countryId));
            return "registration";
        }

        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setLogin(user.getLogin());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setBirthday(Date.valueOf(user.getBirthday()));
        newUser.setConfirmed(true);
        newUser.setRole(2);
        newUser.setIsLocked(false);
        newUser.setCountry(countryService.getCountryById(countryId));
        newUser.setCity(cityService.getCityById(cityId));
        newUser.setGender(genderService.getGenderById(genderId));
        newUser.setDescription(user.getDescription());
        newUser = userService.create(newUser);


        if (!file.isEmpty()) {
            Map options = ObjectUtils.asMap(
                    "cloud_name", "chevron",
                    "api_key", "955731587757792",
                    "api_secret", "_An7669JFG-iRVrBjEdxQ1iwnDY");
            Map uploadResult;
            Cloudinary cloudinary = new Cloudinary(options);
            try {
                uploadResult = cloudinary.uploader().upload(stream2file(file.getInputStream()), options);
                Album album = new Album("Main", newUser);
                album = albumService.create(album);
                Photo photo = new Photo((String) uploadResult.get("url"), true, album);
                photoService.create(photo);
                newUser.setPhotoURL((String) uploadResult.get("url"));
                userService.update(newUser);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        request.getSession().setAttribute("msg", "Registration successful. Please use your login and password");
        return "redirect:/login";
    }

    public File stream2file (InputStream in) throws IOException {
        final File tempFile = File.createTempFile("stream2file", ".tmp");
        tempFile.deleteOnExit();
        FileOutputStream out = new FileOutputStream(tempFile);
        IOUtils.copy(in, out);
        return tempFile;
    }
}
