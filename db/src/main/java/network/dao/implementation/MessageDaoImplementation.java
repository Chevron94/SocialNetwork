package network.dao.implementation;

import network.dao.MessageDao;
import network.entity.Message;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roman on 22.09.2015.
 */
public class MessageDaoImplementation extends GenericDaoImplementation<Message,Long> implements MessageDao {
    public MessageDaoImplementation()
    {
        super(Message.class);
    }

    public Message getMessageById(Long id) {
        String jpa = "SELECT m FROM Message m WHERE m.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        List<Message> messages = this.executeQuery(jpa, parameters);
        return messages.get(0);
    }

    public List<Message> getMessagesByUserId(Long id, Integer start, Integer limit) {
        String jpa = "SELECT m FROM Message m WHERE m.user.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id", id);
        return this.executeQuery(jpa, parameters, start, limit);
    }

    public List<Message> getMessagesByDialogId(Long id, Integer start, Integer limit) {
        String jpa = "SELECT m FROM Message m WHERE m.dialog.id = :id order by m.dateTime desc";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        List<Message> result = this.executeQuery(jpa, parameters, start, limit);
        Collections.reverse(result);
        return result;
    }

    @Override
    public void readMessages(Long idUser, Long idDialog) {
        String jpa = "SELECT m FROM Message m WHERE m.read = false AND m.user.id <> :idUser AND m.dialog.id = :idDialog";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("idUser",idUser);
        parameters.put("idDialog",idDialog);
        List<Message> tmp = this.executeQuery(jpa,parameters);
        for(Message message: tmp){
            message.setRead(true);
            update(message);
        }
    }

    @Override
    public Long getCountUnreadMessagesByUserIdAndDialogId(Long idUser, Long idDialog) {
        em = getEntityManager();
        String jpa = "Select count(m) From Message m where m.user.id <> :idUser AND m.dialog.id = :idDialog";
        Long result = (Long) em.createQuery(jpa).setParameter("idUser", idUser).setParameter("idDialog",idDialog).getSingleResult();
        closeEntityManager();
        return result;
    }
}
