package network.dao;

import network.entity.LanguageLevel;

import javax.ejb.Local;
import javax.ejb.Remote;

/**
 * Created by roman on 22.09.15.
 */
@Local
public interface LanguageLevelDao extends GenericDao<LanguageLevel,Long> {
    public LanguageLevel getLanguageLevelById(Long id);
}
