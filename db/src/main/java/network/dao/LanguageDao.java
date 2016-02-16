package network.dao;

import network.entity.Language;

import javax.ejb.Local;
import javax.ejb.Remote;

/**
 * Created by roman on 22.09.15.
 */
@Local
public interface LanguageDao extends GenericDao<Language,Long> {
    public String getName(Language language);
    public String getPictureUrl(Language language);

    public Language getLanguageById(Long id);
}
