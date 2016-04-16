package network.service;

import network.dao.DialogDao;
import network.dao.implementation.DialogDaoImplementation;
import network.entity.Dialog;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

/**
 * Created by roman on 9/24/15.
 */
@Stateless
public class DialogService extends DialogDaoImplementation implements DialogDao{
    @Override
    protected void closeEntityManager() {}

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @PersistenceContext(unitName = "PERSISTENCE_WEB")
    protected EntityManager em;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Dialog getDialogById(Long id) {
        return super.getDialogById(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Dialog getDialogByTwoUser(Long idUser1, Long idUser2) {
        return super.getDialogByTwoUser(idUser1, idUser2);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Dialog> getDialogsByUserId(Long id, Integer start, Integer count) {
        return super.getDialogsByUserId(id,start,count);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Dialog> getDialogsWithUnreadMessagesByUserId(Long id) {
        return super.getDialogsWithUnreadMessagesByUserId(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public Dialog create(Dialog dialog) {
        return super.create(dialog);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Dialog read(Long id) {
        return super.read(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Dialog> readAll() {
        return super.readAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public Dialog update(Dialog dialog) {
        return super.update(dialog);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public void deleteSeveral(List<Dialog> t) {
        super.deleteSeveral(t);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<Dialog> executeQuery(String jpql, Map<String, Object> parameters) {
        return super.executeQuery(jpql, parameters);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<Dialog> executeQuery(String jpql) {
        return super.executeQuery(jpql);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<Dialog> executeQuery(String jpql, Map<String, Object> parameters, Integer start, Integer limit) {
        return super.executeQuery(jpql, parameters, start, limit);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<Dialog> executeQuery(String jpql, Integer start, Integer limit) {
        return super.executeQuery(jpql, start, limit);
    }
}
