package network.dao;

import network.entity.User;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.Date;
import java.util.List;


/**
 * Created by roman on 22.09.15.
 */
@Local
public interface UserDao extends GenericDao<User,Long> {
    public String getLogin(User user);
    public String getPassword(User user);
    public Date getBirthday(User user);
    public String getEmail(User user);
    public String getPhotoUrl(User user);

    public User Login(String login, String password);
    public User getUserByLogin(String login);
    public User getUserByEmail(String email);
    public User getUserById(Long id);
    public List<User> getUsersByCityId(Long id);
    public List<User> getUsersByCountryId(Long id);
    public List<User> getUsersByGenderId(Long id);
    public List<User> getUsersByCustomFilter(Long idContinent, Long idCountry, Long idCity, boolean isMale, boolean isFemale, int ageFrom, int ageTo, Long idLanguage);
}
