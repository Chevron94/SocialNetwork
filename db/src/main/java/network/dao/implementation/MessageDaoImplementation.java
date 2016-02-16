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

    public String getText(Message message) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("PERSISTENCE");
            em = emf.createEntityManager();
            Long idMessage = message.getId();
            return em.find(Message.class, idMessage).getText();
        } finally {
            if (em != null ) em.close();
            if (emf != null) emf.close();
        }
    }

    public Date getDateTime(Message message) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("PERSISTENCE");
            em = emf.createEntityManager();
            Long idMessage = message.getId();
            return em.find(Message.class, idMessage).getDateTime();
        } finally {
            if (em != null ) em.close();
            if (emf != null) emf.close();
        }
    }

    public boolean getIsRead(Message message) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("PERSISTENCE");
            em = emf.createEntityManager();
            Long idMessage = message.getId();
            return em.find(Message.class, idMessage).isRead();
        } finally {
            if (em != null ) em.close();
            if (emf != null) emf.close();
        }
    }

    public Message getMessageById(Long id) {
        String jpa = "SELECT m FROM Message m WHERE m.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        List<Message> messages = this.executeQuery(jpa, parameters);
        return messages.get(0);
    }

    public List<Message> getMessagesByUserId(Long id) {
        String jpa = "SELECT m FROM Message m WHERE m.user.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id", id);
        return this.executeQuery(jpa, parameters);
    }

    public List<Message> getMessagesByDialogId(Long id) {
        String jpa = "SELECT m FROM Message m WHERE m.dialog.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        return this.executeQuery(jpa, parameters);
    }
}
