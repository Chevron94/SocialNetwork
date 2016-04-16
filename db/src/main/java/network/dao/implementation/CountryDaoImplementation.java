package network.dao.implementation;

import network.dao.CountryDao;
import network.entity.Country;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
public class CountryDaoImplementation extends GenericDaoImplementation<Country,Long> implements CountryDao {
    public CountryDaoImplementation()
    {
        super(Country.class);
    }

    public String getFlagUrl(Country country) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("PERSISTENCE");
            em = emf.createEntityManager();
            Long idCountry = country.getId();
            return em.find(Country.class,idCountry).getFlagURL();
        }finally {
            if (em != null ) em.close();
            if (emf != null) emf.close();
        }
    }

    public Country getCountryById(Long id) {
        String jpa = "SELECT c FROM Country c WHERE c.id= :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        List<Country> countries = this.executeQuery(jpa, parameters);
        return countries.get(0);
    }

    public List<Country> getCountryByContinentId(Long id) {
        String jpa = "SELECT c FROM Country c WHERE c.continent.id= :id ORDER BY c.name";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id", id);
        return this.executeQuery(jpa, parameters);
    }

    @Override
    public List<Country> getCountriesWithUsers() {
        String jpa = "Select DISTINCT c from Country c, User u WHERE c.id = u.country.id order by c.name";
        return this.executeQuery(jpa);
    }

    @Override
    public Country getCountryByIso(String iso) {
        String jpa = "SELECT c FROM Country c WHERE c.iso= :iso";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("iso", iso);
        List<Country> countries = this.executeQuery(jpa, parameters);
        return countries.get(0);
    }

    @Override
    public List<Country> readAll() {
        String jpa = "SELECT c FROM Country c order by c.name";
        return this.executeQuery(jpa);
    }
}
