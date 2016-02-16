package network.dao;

import network.entity.Language;
import network.entity.LanguageLevel;
import network.entity.LanguageUser;
import network.entity.User;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.Collection;

/**
 * Created by roman on 22.09.15.
 */
@Local
public interface LanguageUserDao extends GenericDao<LanguageUser,Long> {
    public Collection getLanguagesByUser(User user);
    public Collection getUsersByLanguage(Language language);
    public LanguageUser getLanguageUserByUserAndLanguageId(Long idUser, Long idLanguage);
}
