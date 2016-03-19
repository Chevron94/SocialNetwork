package network.service;

import network.dao.MessageDao;
import network.dao.implementation.MessageDaoImplementation;
import network.entity.Message;
import network.service.events.MessageEvent;
import network.service.events.NewMessageEvent;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by roman on 9/24/15.
 */
@Stateless
public class MessageService extends MessageDaoImplementation implements MessageDao {

    @Inject
    @NewMessageEvent
    Event<MessageEvent> messageEvent;

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
    public Message getMessageById(Long id) {
        return super.getMessageById(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Message> getMessagesByUserId(Long id, Integer start, Integer limit) {
        return super.getMessagesByUserId(id, start, limit);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Message> getMessagesByDialogId(Long id, Integer start, Integer limit) {
        return super.getMessagesByDialogId(id, start, limit);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public Message create(Message message) {
        message = super.create(message);
        MessageEvent event = new MessageEvent(message);
        messageEvent.fire(event);
        return message;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Message read(Long id) {
        return super.read(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Message> readAll() {
        return super.readAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public Message update(Message message) {
        return super.update(message);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)

    public void delete(List<Message> t) {
        super.delete(t);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<Message> executeQuery(String jpql, Map<String, Object> parameters) {
        return super.executeQuery(jpql, parameters);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<Message> executeQuery(String jpql) {
        return super.executeQuery(jpql);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<Message> executeQuery(String jpql, Map<String, Object> parameters, Integer start, Integer limit) {
        return super.executeQuery(jpql, parameters, start, limit);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<Message> executeQuery(String jpql, Integer start, Integer limit) {
        return super.executeQuery(jpql, start, limit);
    }
}
