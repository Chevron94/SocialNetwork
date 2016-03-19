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
    public Message getMessageById(Long id);

    public List<Message> getMessagesByUserId(Long id, Integer start, Integer limit);
    public List<Message> getMessagesByDialogId(Long id, Integer start, Integer limit);
}
