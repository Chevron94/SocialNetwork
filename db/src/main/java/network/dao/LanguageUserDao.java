package network.dao;

import network.entity.Language;
import network.entity.LanguageLevel;
import network.entity.LanguageUser;
import network.entity.User;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.Collection;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
@Local
public interface LanguageUserDao extends GenericDao<LanguageUser,Long> {
    public List<LanguageUser> getLanguagesByUser(User user, Integer start, Integer limit);
    public List<LanguageUser> getUsersByLanguage(Language language, Integer start, Integer limit);
    public LanguageUser getLanguageUserByUserAndLanguageId(Long idUser, Long idLanguage);
}
