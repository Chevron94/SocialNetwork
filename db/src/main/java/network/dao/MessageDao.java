package network.dao;

import network.entity.Message;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.Date;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
@Local
public interface MessageDao extends GenericDao<Message, Long> {
    public String getText(Message message);
    public Date getDateTime(Message message);
    public boolean getIsRead(Message message);
    public Message getMessageById(Long id);

    public List<Message> getMessagesByUserId(Long id);
    public List<Message> getMessagesByDialogId(Long id);
}
