package network.service;

import network.dao.LanguageUserDao;
import network.dao.implementation.LanguageUserDaoImplementation;
import network.entity.Language;
import network.entity.LanguageUser;
import network.entity.User;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by roman on 9/24/15.
 */
@Stateless
public class LanguageUserService extends LanguageUserDaoImplementation implements LanguageUserDao {

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
    public Collection getLanguagesByUser(User user) {
        return super.getLanguagesByUser(user);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Collection getUsersByLanguage(Language language) {
        return super.getUsersByLanguage(language);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public LanguageUser getLanguageUserByUserAndLanguageId(Long idUser, Long idLanguage) {
        return super.getLanguageUserByUserAndLanguageId(idUser, idLanguage);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public LanguageUser create(LanguageUser languageUser) {
        return super.create(languageUser);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public LanguageUser read(Long id) {
        return super.read(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<LanguageUser> readAll() {
        return super.readAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public LanguageUser update(LanguageUser languageUser) {
        return super.update(languageUser);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public void delete(List<LanguageUser> t) {
        super.delete(t);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<LanguageUser> executeQuery(String jpql, Map<String, Object> parameters) {
        return super.executeQuery(jpql, parameters);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<LanguageUser> executeQuery(String jpql) {
        return super.executeQuery(jpql);
    }
}
