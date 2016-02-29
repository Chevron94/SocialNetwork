package network.dao;

import network.entity.Dialog;
import network.entity.User;
import network.entity.UserDialog;

import javax.ejb.Local;
import java.util.Collection;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
@Local
public interface UserDialogDao extends GenericDao<UserDialog, Long> {
    public List<UserDialog> getUsersByDialog(Dialog dialog);
    public List<UserDialog> getDialogsByUser(User user);
}
