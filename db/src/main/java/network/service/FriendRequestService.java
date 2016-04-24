package network.service;

import network.dao.FriendRequestDao;
import network.dao.implementation.FriendRequestDaoImplementation;
import network.entity.FriendRequest;

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
public class FriendRequestService extends FriendRequestDaoImplementation implements FriendRequestDao {

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
    public FriendRequest getFriendRequestById(Long id) {
        return super.getFriendRequestById(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public FriendRequest getFriendRequestBySenderAndReceiverId(Long sender, Long receiver) {
        return super.getFriendRequestBySenderAndReceiverId(sender, receiver);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public FriendRequest getFriendRequestByTwoUsersId(Long idUser1, Long idUser2) {
        return super.getFriendRequestByTwoUsersId(idUser1, idUser2);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<FriendRequest> getFriendRequestsBySenderId(Long id, Integer start, Integer limit) {
        return super.getFriendRequestsBySenderId(id, start, limit);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<FriendRequest> getFriendRequestsByReceiverId(Long id, Integer start, Integer limit) {
        return super.getFriendRequestsByReceiverId(id, start, limit);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<FriendRequest> getFriendsByUserId(Long id, Integer start, Integer limit) {
        return super.getFriendsByUserId(id, start, limit);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Long getNumberOfReceivedUnacceptedFriendRequests(Long idReceiver) {
        return super.getNumberOfReceivedUnacceptedFriendRequests(idReceiver);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public FriendRequest create(FriendRequest friendRequest) {
        return super.create(friendRequest);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public FriendRequest read(Long id) {
        return super.read(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public FriendRequest update(FriendRequest friendRequest) {
        return super.update(friendRequest);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<FriendRequest> readAll() {
        return super.readAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public void deleteSeveral(List<FriendRequest> t) {
        super.deleteSeveral(t);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<FriendRequest> executeQuery(String jpql, Map<String, Object> parameters) {
        return super.executeQuery(jpql, parameters);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<FriendRequest> executeQuery(String jpql) {
        return super.executeQuery(jpql);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<FriendRequest> executeQuery(String jpql, Map<String, Object> parameters, Integer start, Integer limit) {
        return super.executeQuery(jpql, parameters, start, limit);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<FriendRequest> executeQuery(String jpql, Integer start, Integer limit) {
        return super.executeQuery(jpql, start, limit);
    }
}
