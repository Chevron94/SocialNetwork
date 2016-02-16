package network.service;

import network.dao.GenderDao;
import network.dao.implementation.GenderDaoImplementation;
import network.entity.Gender;

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
public class GenderService extends GenderDaoImplementation implements GenderDao {

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
    public String getName(Gender gender) {
        return super.getName(gender);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Gender getGenderById(Long id) {
        return super.getGenderById(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public Gender create(Gender gender) {
        return super.create(gender);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Gender read(Long id) {
        return super.read(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Gender> readAll() {
        return super.readAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public Gender update(Gender gender) {
        return super.update(gender);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public void delete(List<Gender> t) {
        super.delete(t);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<Gender> executeQuery(String jpql, Map<String, Object> parameters) {
        return super.executeQuery(jpql, parameters);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<Gender> executeQuery(String jpql) {
        return super.executeQuery(jpql);
    }
}
