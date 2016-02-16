package network.dao.implementation;

import network.dao.UserDao;
import network.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
public class UserDaoImplementation extends GenericDaoImplementation<User,Long> implements UserDao {
    public UserDaoImplementation()
    {
        super(User.class);
    }

    public String getLogin(User user) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("PERSISTENCE");
            em = emf.createEntityManager();
            Long idUser = user.getId();
            return em.find(User.class, idUser).getLogin();
        } finally {
            if (em != null ) em.close();
            if (emf != null) emf.close();
        }
    }

    public String getPassword(User user) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("PERSISTENCE");
            em = emf.createEntityManager();
            Long idUser = user.getId();
            return em.find(User.class, idUser).getPassword();
        } finally {
            if (em != null ) em.close();
            if (emf != null) emf.close();
        }
    }

    public Date getBirthday(User user) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("PERSISTENCE");
            em = emf.createEntityManager();
            Long idUser = user.getId();
            return em.find(User.class, idUser).getBirthday();
        } finally {
            if (em != null ) em.close();
            if (emf != null) emf.close();
        }
    }

    public String getEmail(User user) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("PERSISTENCE");
            em = emf.createEntityManager();
            Long idUser = user.getId();
            return em.find(User.class, idUser).getEmail();
        } finally {
            if (em != null ) em.close();
            if (emf != null) emf.close();
        }
    }

    public String getPhotoUrl(User user) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("PERSISTENCE");
            em = emf.createEntityManager();
            Long idUser = user.getId();
            return em.find(User.class, idUser).getPhotoURL();
        } finally {
            if (em != null ) em.close();
            if (emf != null) emf.close();
        }
    }

    public User Login(String login, String password) {
        String jpa = "SELECT u FROM User u WHERE u.login = :login and u.password = :password";
        HashMap<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("login", login);
        parameters.put("password", password);
        List<User> users = this.executeQuery(jpa, parameters);
        if (users.size() != 1){
            return null;
        }
        return users.get(0);
    }

    public User getUserByLogin(String login) {
        String jpa = "SELECT u FROM User u WHERE u.login = :login";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("login",login);
        List<User> users = this.executeQuery(jpa, parameters);
        if (users.size() == 0){
            return null;
        }
        return users.get(0);
    }

    public User getUserByEmail(String email) {
        String jpa = "SELECT u FROM User u WHERE u.email = :email";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("email",email);
        List<User> users = this.executeQuery(jpa, parameters);
        if (users.size() == 0){
            return null;
        }
        return users.get(0);
    }

    public User getUserById(Long id) {
        String jpa = "SELECT u FROM User u WHERE u.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        List<User> users = this.executeQuery(jpa, parameters);
        if (users.size() == 0){
            return null;
        }
        return users.get(0);
    }

    public List<User> getUsersByCityId(Long id) {
        String jpa = "SELECT u FROM User u WHERE u.city.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        return this.executeQuery(jpa, parameters);
    }

    @Override
    public List<User> getUsersByGenderId(Long id) {
        String jpa = "SELECT u FROM User u WHERE u.gender.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        return this.executeQuery(jpa, parameters);
    }

    @Override
    public List<User> getUsersByCountryId(Long id) {
        String jpa = "SELECT u FROM User u WHERE u.country.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        return this.executeQuery(jpa, parameters);
    }

    @Override
    public List<User> getUsersByCustomFilter(Long idContinent, Long idCountry, Long idCity, boolean isMale, boolean isFemale, int ageFrom, int ageTo, Long idLanguage) {
        String jpa = "SELECT u FROM User u ";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        if (idCity > 0) {
            jpa += "WHERE u.city.id = :idCity ";
            parameters.put("idCity", idCity);
        }else
        {
            if (idCountry > 0)
            {
                jpa += "WHERE u.country.id = :idCountry ";
                parameters.put("idCountry", idCountry);
            }else
            {
                if (idContinent > 0) {
                    jpa += "WHERE u.country.continent.id = :idContinent ";
                    parameters.put("idContinent", idContinent);
                }else jpa += "WHERE 1 = 1 ";
            }
        }
        if (isFemale != isMale)
        {
            jpa += isMale ? "AND u.gender.id = 1 " : "AND u.gender.id = 2 ";
        }
        if (ageFrom > ageTo)
        {
            int tmp = ageTo;
            ageTo = ageFrom;
            ageFrom = tmp;
        }
        Date date = new Date();
        jpa += "AND DATEDIFF(year, u.birthday, :date) >= :ageFrom AND DATEDIFF(year, u.birthday, :date) <= :ageTo";
        parameters.put("date",date);
        parameters.put("ageFrom", ageFrom);
        parameters.put("ageTo",ageTo);
        return this.executeQuery(jpa, parameters);
    }
}
