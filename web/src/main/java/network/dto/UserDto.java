package network.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by roman on 10/1/15.
 */
public class UserDto {
    @Size(min = 3, message = "Поле \"Login\" должно быть длиннее 3 символов")
    private String login;
    @Size(min = 8, message = "Поле \"Password\" должно быть длиннее 8 символов")
    private String password;
    private String Oldpassword;
    @Size(min = 1, message = "Поле \"Name\" не заполнено")
    private String name;
    @Size(min = 1, message = "Поле \"Photo\" не заполнено")
    private String photo;
    @Size(min = 1, message = "Поле \"E-mail\" не заполнено")
    private String email;
    @NotNull(message = "Поле \"Birthday\" не заполнено")
    private String birthday;

    @NotNull(message = "Поле \"Gender\" не заполнено")
    private Long gender;
    @NotNull(message = "Поле \"City\" не заполнено")
    private Long city;
    @NotNull(message = "Поле \"Country\" не заполнено")
    private Long country;

    List<Long> languages;
    List<Long> languageLevels;

    @Size(min=50, message = "Поле \"About me\" должно содержать не менее 50 символов")
    private String description;

    public List<Long> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Long> languages) {
        this.languages = languages;
    }

    public List<Long> getLanguageLevels() {
        return languageLevels;
    }

    public void setLanguageLevels(List<Long> languageLevels) {
        this.languageLevels = languageLevels;
    }



    public UserDto() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getGender() {
        return gender;
    }

    public void setGender(Long gender) {
        this.gender = gender;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOldpassword() {
        return Oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        Oldpassword = oldpassword;
    }
}
