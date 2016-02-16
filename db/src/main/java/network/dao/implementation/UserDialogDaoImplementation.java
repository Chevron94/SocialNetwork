package network.dao.implementation;

import network.dao.UserDialogDao;
import network.entity.Dialog;
import network.entity.User;
import network.entity.UserDialog;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by roman on 22.09.15.
 */
public class UserDialogDaoImplementation extends GenericDaoImplementation<UserDialog, Long> implements UserDialogDao {
    public UserDialogDaoImplementation()
    {
        super(UserDialog.class);
    }

    public Collection getUsersByDialog(Dialog dialog) {
        String jpa = "select u from UserDialog u WHERE u.dialog = :dialog";
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("dialog",dialog);
        return this.executeQuery(jpa, parameters);
    }

    public Collection getDialogsByUser(User user) {
        String jpa = "select u from UserDialog u WHERE u.user = :user";
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("user",user);
        return this.executeQuery(jpa, parameters);
    }
}
