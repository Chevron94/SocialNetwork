package network.service;

import network.dao.LanguageDao;
import network.dao.implementation.LanguageDaoImplementation;
import network.entity.Language;

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
public class LanguageService extends LanguageDaoImplementation implements LanguageDao {

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
    public Language getLanguageById(Long id) {
        return super.getLanguageById(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public Language create(Language language) {
        return super.create(language);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Language read(Long id) {
        return super.read(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Language> readAll() {
        return super.readAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public Language update(Language language) {
        return super.update(language);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public void delete(List<Language> t) {
        super.delete(t);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<Language> executeQuery(String jpql, Map<String, Object> parameters) {
        return super.executeQuery(jpql, parameters);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<Language> executeQuery(String jpql) {
        return super.executeQuery(jpql);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<Language> executeQuery(String jpql, Map<String, Object> parameters, Integer start, Integer limit) {
        return super.executeQuery(jpql, parameters, start, limit);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<Language> executeQuery(String jpql, Integer start, Integer limit) {
        return super.executeQuery(jpql, start, limit);
    }
}
