package network.service;

import network.dao.FileDao;
import network.dao.implementation.FIleDaoImplementation;
import network.entity.File;

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
public class FileService extends FIleDaoImplementation implements FileDao{

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
    public File getFileById(Long id) {
        return super.getFileById(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<File> getFilesByMessageId(Long id) {
        return super.getFilesByMessageId(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public File create(File file) {
        return super.create(file);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public File read(Long id) {
        return super.read(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<File> readAll() {
        return super.readAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public File update(File file) {
        return super.update(file);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public void delete(List<File> t) {
        super.delete(t);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<File> executeQuery(String jpql, Map<String, Object> parameters) {
        return super.executeQuery(jpql, parameters);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<File> executeQuery(String jpql) {
        return super.executeQuery(jpql);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<File> executeQuery(String jpql, Map<String, Object> parameters, Integer start, Integer limit) {
        return super.executeQuery(jpql, parameters, start, limit);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<File> executeQuery(String jpql, Integer start, Integer limit) {
        return super.executeQuery(jpql, start, limit);
    }
}
