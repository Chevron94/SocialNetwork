package network.dao.implementation;

import network.dao.MessageDao;
import network.entity.Message;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
        String jpa = "SELECT m FROM Message m WHERE m.dialog.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        return this.executeQuery(jpa, parameters, start, limit);
    }
}
