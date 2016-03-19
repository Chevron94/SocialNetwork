package network.dao.implementation;

import network.dao.DialogDao;
import network.entity.Dialog;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
public class DialogDaoImplementation extends GenericDaoImplementation<Dialog,Long> implements DialogDao {
    public DialogDaoImplementation()
    {
        super(Dialog.class);
    }

    public Dialog getDialogById(Long id) {
        String jpa = "SELECT d FROM Dialog d WHERE d.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        List<Dialog> dialogs = this.executeQuery(jpa, parameters);
        return dialogs.get(0);
    }

    @Override
    public Dialog getDialogByTwoUser(Long idUser1, Long idUser2) {
        String jpa = "SELECT u1.dialog " +
                "FROM UserDialog u1, UserDialog u2 " +
                "WHERE u1.dialog = u2.dialog AND " +
                "u1.user.id = :idUser1 AND u2.user.id = :idUser2 AND " +
                "2 = (SELECT COUNT(u3) FROM UserDialog u3 WHERE u3.dialog.id = u1.dialog.id)";
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("idUser1",idUser1);
        parameters.put("idUser2",idUser2);
        try{
            return this.executeQuery(jpa,parameters).get(0);
        }catch (Exception e) {
            return null;
        }
    }
}
