package network.controllers;

import com.sun.org.apache.xpath.internal.operations.Bool;
import network.dao.*;
import network.dto.LanguageDto;
import network.dto.UserDto;
import network.entity.*;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import network.services.MD5Service;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by roman on 10/7/15.
 */
@Controller
public class RegistrationController {
    private static final Logger logger = Logger.getRootLogger();
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
    @EJB
    LanguageDao languageService;
    @EJB
    LanguageLevelDao languageLevelService;
    @EJB
    LanguageUserDao languageUserService;

    @RequestMapping(value = "/registration/citiesByCountry", method = RequestMethod.GET)
    public @ResponseBody
    List<City> getCities(
            @RequestParam(value="searchId") Long searchId,
            @RequestParam(value="name") String name) {

        return cityService.getCitiesByCountryIdAndPartOfCityName(searchId, name);
    }

    @RequestMapping(value = "/registration/countriesByContinent", method = RequestMethod.GET)
    public @ResponseBody
    List<Country> getCountries(
            @RequestParam(value="searchId") Long searchId) {

        return countryService.getCountryByContinentId(searchId);
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
        model.addAttribute("countries",countryService.readAll());
        model.addAttribute("genders",genderService.readAll());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String setRegistration(@ModelAttribute("user") @Valid UserDto user,
                                  BindingResult bindingResult,
                                  Model model,
                                  HttpServletRequest request,
                                  @RequestParam("filePhoto") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            List<String> validationErrors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                validationErrors.add(error.getDefaultMessage());
            }
        }
        List<String> validationErrors = new ArrayList<>();
        if (userService.getUserByLogin(user.getLogin()) != null) {
            validationErrors.add("Login is already used");
        }

        Pattern loginPattern = Pattern.compile("^\\w+$");
        Pattern emailPattern = Pattern.compile("^\\w+@\\w+\\.\\w+");
        Matcher m = loginPattern.matcher(user.getLogin());
        if (!m.matches()) {
            validationErrors.add("Login must be contains only latin symbols and digits");
        }
        m = loginPattern.matcher(user.getName());
        if (!m.matches()) {
            validationErrors.add("Name must be contains only latin symbols and digits");
        }
        m = emailPattern.matcher(user.getEmail());
        if (!m.matches()){
            validationErrors.add("Incorrect email");
        }
        user.getCountry();
        user.getCity();
        user.getGender();
        if (user.getCountry() == 0) {
            validationErrors.add("Please select country");
        }

        if (user.getBirthday().equals("")) {
            validationErrors.add("Please select date");
        }

        if (user.getCity() == 0) {
            validationErrors.add("Please select city");
        }

        if (user.getGender() == 0) {
            validationErrors.add("Please select gender");
        }

        if (validationErrors.size() > 0) {
            model.addAttribute("countries", countryService.readAll());
            model.addAttribute("genders", genderService.readAll());
            model.addAttribute("user", user);
            model.addAttribute("errors", validationErrors);
            if (user.getCountry() != 0 && user.getCity() != 0)
                model.addAttribute("city", cityService.getCityById(user.getCity()));
            return "registration";
        }

        User newUser = new User();
        newUser.setName(user.getName().trim());
        newUser.setLogin(user.getLogin().trim());
        newUser.setPassword(MD5Service.md5(user.getPassword()));
        newUser.setEmail(user.getEmail().trim());
        newUser.setBirthday(Date.valueOf(user.getBirthday()));
        newUser.setConfirmed(false);
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setRole(2);
        newUser.setIsLocked(false);
        newUser.setCountry(countryService.getCountryById(user.getCountry()));
        newUser.setCity(cityService.getCityById(user.getCity()));
        newUser.setGender(genderService.getGenderById(user.getGender()));
        newUser.setDescription(user.getDescription());
        newUser = userService.create(newUser);
        Album album = new Album("Main", newUser, new java.util.Date());
        album = albumService.create(album);
        if (!file.isEmpty()) {
            Map options = ObjectUtils.asMap(
                    "cloud_name", "chevron",
                    "api_key", "955731587757792",
                    "api_secret", "_An7669JFG-iRVrBjEdxQ1iwnDY");
            Map uploadResult;
            Cloudinary cloudinary = new Cloudinary(options);
            try {
                uploadResult = cloudinary.uploader().upload(stream2file(file.getInputStream()), options);
                Photo photo = new Photo((String) uploadResult.get("secure_url"),new java.util.Date(), album);
                photoService.create(photo);
                newUser.setPhotoURL((String) uploadResult.get("secure_url"));
                userService.update(newUser);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            newUser.setPhotoURL("/resources/images/system/no-photo.png");
            userService.update(newUser);
        }
        sendMail(newUser.getEmail(),"Registration","Thanks for signing up for Hello From! Please click the link below to confirm your email address.\n"+"https://www.hello-from.tk/activate/"+newUser.getToken());
        request.getSession().setAttribute("msg", "Please, check your email for instructions");
        return "redirect:/login";
    }

    @RequestMapping(value = "/activate/{token}", method = RequestMethod.GET)
    public String setLanguages(@PathVariable("token") String token, HttpServletRequest request, Model model){
        User user = userService.getUserByToken(token, false);
        if (user!= null){
            List<Language> languages = languageService.readAll();
            List<LanguageLevel> languageLevels = languageLevelService.readAll();
            model.addAttribute("languages",languages);
            model.addAttribute("languageLevels",languageLevels);
            return "completeRegistration";
        }else {
            request.getSession().setAttribute("error", "Incorrect token");
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/activate/{token}", method = RequestMethod.POST)
    public String activateUser(@PathVariable("token") String token, @ModelAttribute("languagesForm") LanguageDto languageDto, HttpServletRequest request, Model model){
        User user = userService.getUserByToken(token, false);
        if (user!= null){
            user.setConfirmed(true);
            user.setToken(null);
            user = userService.update(user);
            HashSet<Long> used = new HashSet<>();
            for(int i = 0; i<languageDto.getLanguages().size(); i++){
                if (languageDto.getLanguages().get(i) > 0 && languageDto.getLanguageLevels().get(i) > 0 && !used.contains(languageDto.getLanguages().get(i))){
                    languageUserService.create(new LanguageUser(user, languageService.getLanguageById(languageDto.getLanguages().get(i)), languageLevelService.getLanguageLevelById(languageDto.getLanguageLevels().get(i))));
                    used.add(languageDto.getLanguages().get(i));
                }
            }
            if(used.isEmpty()){
                model.addAttribute("error", "You should select at least one language with level");
                return "completeRegistration";
            }
            request.getSession().setAttribute("msg", "Your account is active. You can login now");
            return "redirect:/login";
        }else {
            request.getSession().setAttribute("error", "Incorrect token");
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/resetRequest", method = RequestMethod.POST)
    public String checkPasswordReset(HttpServletRequest request){
        String email = request.getParameter("emailInput").trim();
        User user = userService.getUserByEmail(email);
        if (user == null){
            request.getSession().setAttribute("error", "Email not found");
            return "redirect:/login";
        }

        user.setToken(UUID.randomUUID().toString());
        userService.update(user);
        sendMail(user.getEmail(),"Reset password","We have received a password reset request for your Hello From account (hopefully by you).\n" +
                "Your login: "+user.getLogin()+"\n"+
                "Please click the link below to reset your password.\n" +
                "https://www.hello-from.tk/reset/"+user.getToken()+"\nIf you didn't send password reset request, ignore this message.");
        request.getSession().setAttribute("msg", "Please, check your email for instructions");
        return "redirect:/login";
    }

    @RequestMapping(value = "/reset/{token}", method = RequestMethod.GET)
    public String resetPassword(@PathVariable("token") String token, HttpServletRequest request){
        User user = userService.getUserByToken(token, true);
        if (user!= null){
            return "passwordReset";
        }else {
            request.getSession().setAttribute("error", "Incorrect token");
            return "redirect:/login";
        }
    }
    @RequestMapping(value = "/reset/{token}", method = RequestMethod.POST)
    public String updatePassword(@PathVariable("token") String token, HttpServletRequest request){
        User user = userService.getUserByToken(token, true);
        if (user!= null){
            user.setPassword(MD5Service.md5(request.getParameter("password")));
            user.setToken("");
            userService.update(user);
            request.getSession().setAttribute("msg", "Your password was changed. You can login now");
            return "redirect:/login";
        }else {
            request.getSession().setAttribute("error", "Incorrect token");
            return "redirect:/login";
        }
    }

    public File stream2file (InputStream in) throws IOException {
        final File tempFile = File.createTempFile("stream2file", ".tmp");
        tempFile.deleteOnExit();
        FileOutputStream out = new FileOutputStream(tempFile);
        IOUtils.copy(in, out);
        return tempFile;
    }

    public static void sendMail(String receiver, String topic, String text){
        try{
            String command = "echo \""+text+"\" | mail -s \""+topic+"\" "+receiver+" -aFrom:no-reply@hello-from.tk";
            Process proc = Runtime.getRuntime().exec(new String[]{"bash","-c",command});
        }catch (Exception e){
            logger.error(e.fillInStackTrace());
        }
    }
}
