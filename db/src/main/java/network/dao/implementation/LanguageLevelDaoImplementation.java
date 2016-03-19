package network.dao.implementation;

import network.dao.LanguageLevelDao;
import network.entity.LanguageLevel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
public class LanguageLevelDaoImplementation extends GenericDaoImplementation<LanguageLevel, Long> implements LanguageLevelDao {
    public LanguageLevelDaoImplementation()
    {
        super(LanguageLevel.class);
    }

    public LanguageLevel getLanguageLevelById(Long id) {
        String jpa = "SELECT l FROM LanguageLevel l WHERE l.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        List<LanguageLevel> languageLevels = this.executeQuery(jpa, parameters);
        return languageLevels.get(0);
    }
}
