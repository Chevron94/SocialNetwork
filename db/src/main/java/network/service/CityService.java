package network.service;

import network.dao.CityDao;
import network.dao.implementation.CityDaoImplementation;
import network.entity.City;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

/**
 * Created by roman on 9/24/15.
 */
@Stateless
public class CityService extends CityDaoImplementation implements CityDao {

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected void closeEntityManager() {

    }

    @PersistenceContext(unitName = "PERSISTENCE_WEB")
    protected EntityManager em;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public String getName(City city) {
        return super.getName(city);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public City getCityById(Long id) {
        return super.getCityById(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<City> getCitiesByCountryId(Long id) {
        return super.getCitiesByCountryId(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public City create(City city) {
        return super.create(city);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public City read(Long id) {
        return super.read(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<City> readAll() {
        return super.readAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public City update(City city) {
        return super.update(city);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public void delete(List<City> t) {
        super.delete(t);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<City> executeQuery(String jpql, Map<String, Object> parameters) {
        return super.executeQuery(jpql, parameters);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<City> executeQuery(String jpql) {
        return super.executeQuery(jpql);
    }
}
