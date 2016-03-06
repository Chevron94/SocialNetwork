package network.dao;

import network.entity.FriendRequest;
import network.entity.User;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
@Local
public interface FriendRequestDao extends GenericDao<FriendRequest,Long> {
    public boolean getConfirmation(FriendRequest friendRequest);

    public FriendRequest getFriendRequestById(Long id);
    public FriendRequest getFriendRequestBySenderAndReceiverId(Long sender, Long receiver);

    public List<FriendRequest> getFriendRequestsBySenderId(Long id);
    public List<FriendRequest> getFriendRequestsByReceiverId(Long id);

    public List<FriendRequest> getFriendsByUserId(Long id);

}