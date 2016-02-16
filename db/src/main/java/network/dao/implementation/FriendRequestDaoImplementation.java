package network.dao.implementation;

import network.dao.FriendRequestDao;
import network.entity.FriendRequest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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

    public boolean getConfirmation(FriendRequest friendRequest) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("PERSISTENCE");
            em = emf.createEntityManager();
            Long idFriendRequest = friendRequest.getId();
            return em.find(FriendRequest.class, idFriendRequest).isConfirmed();
        } finally {
            if (em != null ) em.close();
            if (emf != null) emf.close();
        }
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

    public List<FriendRequest> getFriendRequestsBySenderId(Long id) {
        String jpa = "SELECT f FROM FriendRequest f WHERE f.sender.id = :id AND f.confirmed='FALSE'";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id", id);
        return this.executeQuery(jpa, parameters);
    }

    public List<FriendRequest> getFriendRequestsByReceiverId(Long id) {
        String jpa = "SELECT f FROM FriendRequest f WHERE f.receiver.id = :id AND f.confirmed='FALSE'";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        return this.executeQuery(jpa, parameters);
    }

    @Override
    public List<FriendRequest> getFriendsByUserId(Long id) {
        String jpa = "SELECT f FROM FriendRequest f WHERE (f.receiver.id = :id " +
                    "OR f.sender.id =:id) " +
                    "AND f.confirmed='TRUE'";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        return this.executeQuery(jpa, parameters);
    }
}
