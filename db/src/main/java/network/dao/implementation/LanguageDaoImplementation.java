package network.dao.implementation;

import network.dao.LanguageDao;
import network.entity.Language;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
public class LanguageDaoImplementation extends GenericDaoImplementation<Language,Long> implements LanguageDao {
    public LanguageDaoImplementation()
    {
        super(Language.class);
    }

    public String getName(Language language) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("PERSISTENCE");
            em = emf.createEntityManager();
            Long idLanguage = language.getId();
            return em.find(Language.class, idLanguage).getName();
        } finally {
            if (em != null ) em.close();
            if (emf != null) emf.close();
        }
    }

    public String getPictureUrl(Language language) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("PERSISTENCE");
            em = emf.createEntityManager();
            Long idLanguage = language.getId();
            return em.find(Language.class, idLanguage).getPictureURL();
        } finally {
            if (em != null ) em.close();
            if (emf != null) emf.close();
        }
    }

    public Language getLanguageById(Long id) {
        String jpa = "SELECT l FROM Language l WHERE l.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        List<Language> languages = this.executeQuery(jpa, parameters);
        return languages.get(0);
    }
}
