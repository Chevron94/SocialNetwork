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

    public String getName(Dialog dialog) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try
        {
            emf = Persistence.createEntityManagerFactory("PERSISTENCE");
            em = emf.createEntityManager();
            Long idDialog = dialog.getId();
            return em.find(Dialog.class,idDialog).getName();
        }
        finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }

    public Dialog getDialogById(Long id) {
        String jpa = "SELECT d FROM Dialog d WHERE d.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        List<Dialog> dialogs = this.executeQuery(jpa, parameters);
        return dialogs.get(0);
    }
}
