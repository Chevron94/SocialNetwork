package network.dao.implementation;

import network.dao.CityDao;
import network.entity.City;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
public class CityDaoImplementation extends GenericDaoImplementation<City,Long> implements CityDao {
    public CityDaoImplementation()
    {
        super(City.class);
    }

    public String getName(City city) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("PERSISTENCE");
            em = emf.createEntityManager();
            Long idCity = city.getId();
            return em.find(City.class, idCity).getName();
        } finally {
            if (em != null ) em.close();
            if (emf != null) emf.close();
        }
    }

    public City getCityById(Long id) {
        String jpa = "SELECT c FROM City c WHERE c.id = :id";
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        List<City> cities = this.executeQuery(jpa, parameters);
        return cities.get(0);
    }

    public List<City> getCitiesByCountryId(Long id) {
        String jpa = "SELECT c FROM City c WHERE c.country.id = :id ORDER BY c.name";
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        return this.executeQuery(jpa, parameters);
    }
}
