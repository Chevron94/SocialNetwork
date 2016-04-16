package network.service;

import network.dao.CountryDao;
import network.dao.implementation.CountryDaoImplementation;
import network.entity.Country;

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
public class CountryService extends CountryDaoImplementation implements CountryDao {

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
    public String getFlagUrl(Country country) {
        return super.getFlagUrl(country);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Country getCountryById(Long id) {
        return super.getCountryById(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Country getCountryByIso(String iso) {
        return super.getCountryByIso(iso);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Country> getCountryByContinentId(Long id) {
        return super.getCountryByContinentId(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Country> getCountriesWithUsers() {
        return super.getCountriesWithUsers();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public Country create(Country country) {
        return super.create(country);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Country read(Long id) {
        return super.read(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Country> readAll() {
        return super.readAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public Country update(Country country) {
        return super.update(country);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public void delete(Long id) {
        super.delete(id);
    }

    @Override

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteSeveral(List<Country> t) {
        super.deleteSeveral(t);
    }

    @Override

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<Country> executeQuery(String jpql, Map<String, Object> parameters) {
        return super.executeQuery(jpql, parameters);
    }

    @Override

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<Country> executeQuery(String jpql) {
        return super.executeQuery(jpql);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<Country> executeQuery(String jpql, Map<String, Object> parameters, Integer start, Integer limit) {
        return super.executeQuery(jpql, parameters, start, limit);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<Country> executeQuery(String jpql, Integer start, Integer limit) {
        return super.executeQuery(jpql, start, limit);
    }
}
