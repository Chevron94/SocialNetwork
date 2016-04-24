package network.dao;

import network.entity.User;

import javax.ejb.Local;
import java.util.HashMap;
import java.util.List;


/**
 * Created by roman on 22.09.15.
 */
@Local
public interface UserDao extends GenericDao<User,Long> {
    public User Login(String login, String password);
    public User getUserByLogin(String login);
    public User getUserByEmail(String email);
    public User getUserById(Long id);
    public User getUserByToken(String token, Boolean confirmed);
    public List<User> getUsersByCityId(Long id, Integer start, Integer limit);
    public List<User> getUsersByCountryId(Long id, Integer start, Integer limit);
    public List<User> getUsersByGenderId(Long id, Integer start, Integer limit);
    public List<User> getUsersByCustomFilter(Long idUser, HashMap<String,Object> params, Integer start, Integer limit);
}
