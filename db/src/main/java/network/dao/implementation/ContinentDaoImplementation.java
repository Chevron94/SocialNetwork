package network.dao.implementation;

import network.dao.ContinentDao;
import network.entity.Continent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
public class ContinentDaoImplementation extends GenericDaoImplementation<Continent, Long> implements ContinentDao {
    public ContinentDaoImplementation()
    {
        super(Continent.class);
    }

    public String getName(Continent continent) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try
        {
            emf = Persistence.createEntityManagerFactory("PERSISTENCE");
            em = emf.createEntityManager();
            Long idContinent = continent.getId();
            return em.find(Continent.class, idContinent).getName();
        }
        finally {
            if (em != null ) em.close();
            if (emf != null) emf.close();
        }
    }

    public Continent getContinentById(Long id) {
        String jpa = "SELECT c FROM Continent c WHERE c.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        List<Continent> continents = this.executeQuery(jpa, parameters);
        return continents.get(0);
    }
}
