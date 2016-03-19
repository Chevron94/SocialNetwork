package network.dao.implementation;

import network.dao.UserDialogDao;
import network.entity.Dialog;
import network.entity.User;
import network.entity.UserDialog;
import org.hibernate.criterion.CriteriaQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
public class UserDialogDaoImplementation extends GenericDaoImplementation<UserDialog, Long> implements UserDialogDao {
    public UserDialogDaoImplementation()
    {
        super(UserDialog.class);
    }

    public List<UserDialog> getUsersByDialog(Dialog dialog) {
        String jpa = "select u from UserDialog u WHERE u.dialog = :dialog";
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("dialog",dialog);
        return this.executeQuery(jpa, parameters);
    }

    public List<UserDialog> getDialogsByUser(User user, Integer start, Integer limit) {
        String jpa = "SELECT ud FROM UserDialog ud WHERE ud.user = :user " +
                "ORDER BY ud.dialog.lastMessageDate DESC";
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("user",user);
        return this.executeQuery(jpa, parameters, start, limit);
    }

}
