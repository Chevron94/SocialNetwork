package network.service;

import network.dao.UserDao;
import network.dao.implementation.UserDaoImplementation;
import network.entity.User;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by roman on 9/24/15.
 */
@Stateless
public class UserService extends UserDaoImplementation implements UserDao {

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
    public User Login(String login, String password) {
        return super.Login(login, password);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public User getUserByLogin(String login) {
        return super.getUserByLogin(login);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public User getUserByEmail(String email) {
        return super.getUserByEmail(email);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public User getUserById(Long id) {
        return super.getUserById(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<User> getUsersByCityId(Long id, Integer start, Integer limit) {
        return super.getUsersByCityId(id,start,limit);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<User> getUsersByGenderId(Long id, Integer start, Integer limit) {
        return super.getUsersByGenderId(id, start, limit);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<User> getUsersByCustomFilter(Long idUser, HashMap<String, Object> params, Integer start, Integer limit) {
        return super.getUsersByCustomFilter(idUser, params, start, limit);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public User getUserByToken(String token, Boolean confirmed) {
        return super.getUserByToken(token, confirmed);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public User create(User user) {
        return super.create(user);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public User read(Long id) {
        return super.read(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<User> readAll() {
        return super.readAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public User update(User user) {
        return super.update(user);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteSeveral(List<User> t) {
        super.deleteSeveral(t);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<User> executeQuery(String jpql, Map<String, Object> parameters) {
        return super.executeQuery(jpql, parameters);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<User> executeQuery(String jpql) {
        return super.executeQuery(jpql);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<User> getUsersByCountryId(Long id, Integer start, Integer limit) {
        return super.getUsersByCountryId(id, start, limit);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<User> executeQuery(String jpql, Map<String, Object> parameters, Integer start, Integer limit) {
        return super.executeQuery(jpql, parameters, start, limit);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<User> executeQuery(String jpql, Integer start, Integer limit) {
        return super.executeQuery(jpql, start, limit);
    }
}
