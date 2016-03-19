package network.dao.implementation;

import network.dao.LanguageUserDao;
import network.entity.Language;
import network.entity.LanguageUser;
import network.entity.User;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by roman on 22.09.15.
 */
public class LanguageUserDaoImplementation extends GenericDaoImplementation<LanguageUser,Long> implements LanguageUserDao {
    public LanguageUserDaoImplementation()
    {
        super(LanguageUser.class);
    }

    public Collection getLanguagesByUser(User user, Integer start, Integer limit) {
        String jpa = "select l from LanguageUser l where l.user = :user";
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("user",user);
        return this.executeQuery(jpa, parameters, start, limit);
    }

    public Collection getUsersByLanguage(Language language, Integer start, Integer limit) {
        String jpa = "select l from LanguageUser l where l.language = :language";
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("language",language);
        return this.executeQuery(jpa, parameters, start, limit);
    }

    public LanguageUser getLanguageUserByUserAndLanguageId(Long idUser, Long idLanguage) {
        String jpa = "select l from LanguageUser l where l.user.id = :idUser AND l.language.id= :idLanguage";
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("idUser",idUser);
        parameters.put("idLanguage",idLanguage);
        return this.executeQuery(jpa, parameters).get(0);
    }
}
