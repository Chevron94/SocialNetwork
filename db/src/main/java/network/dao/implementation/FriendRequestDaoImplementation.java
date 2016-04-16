package network.dao.implementation;

import network.dao.FriendRequestDao;
import network.entity.FriendRequest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
public class FriendRequestDaoImplementation extends GenericDaoImplementation<FriendRequest, Long> implements FriendRequestDao {
    public FriendRequestDaoImplementation()
    {
        super(FriendRequest.class);
    }

    public FriendRequest getFriendRequestById(Long id) {
        String jpa = "SELECT d FROM Dialog d WHERE d.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        List<FriendRequest> dialogs = this.executeQuery(jpa, parameters);
        return dialogs.get(0);
    }

    public FriendRequest getFriendRequestBySenderAndReceiverId(Long sender, Long receiver) {
        String jpa = "SELECT f FROM FriendRequest f WHERE f.receiver.id = :receiver AND f.sender.id = :sender";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("sender",sender);
        parameters.put("receiver",receiver);
        List<FriendRequest> dialogs = this.executeQuery(jpa, parameters);
        if (dialogs.size() == 0)
            return null;
        return dialogs.get(0);
    }

    @Override
    public FriendRequest getFriendRequestByTwoUsersId(Long idUser1, Long idUser2) {
        String jpa = "SELECT f FROM FriendRequest f WHERE (f.receiver.id = :idUser1 AND f.sender.id = :idUser2) OR (f.sender.id = :idUser1 AND f.receiver.id = :idUser2)";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("idUser1",idUser1);
        parameters.put("idUser2",idUser2);
        List<FriendRequest> dialogs = this.executeQuery(jpa, parameters);
        if (dialogs.size() == 0)
            return null;
        return dialogs.get(0);
    }

    public List<FriendRequest> getFriendRequestsBySenderId(Long id, Integer start, Integer limit) {
        String jpa = "SELECT f FROM FriendRequest f WHERE f.sender.id = :id AND f.confirmed='FALSE'";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id", id);
        return this.executeQuery(jpa, parameters, start, limit);
    }

    public List<FriendRequest> getFriendRequestsByReceiverId(Long id, Integer start, Integer limit) {
        String jpa = "SELECT f FROM FriendRequest f WHERE f.receiver.id = :id AND f.confirmed='FALSE'";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        return this.executeQuery(jpa, parameters, start, limit);
    }

    public List<FriendRequest> getFriendsByUserId(Long id, Integer start, Integer limit) {
        String jpa = "SELECT f FROM FriendRequest f WHERE (f.receiver.id = :id " +
                    "OR f.sender.id =:id) " +
                    "AND f.confirmed='TRUE'";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        return this.executeQuery(jpa, parameters, start, limit);
    }
}
