package network.dao;

import network.entity.Dialog;

import javax.ejb.Local;
import javax.ejb.Remote;

/**
 * Created by roman on 22.09.15.
 */
@Local
public interface DialogDao extends GenericDao<Dialog,Long> {
    public Dialog getDialogById(Long id);
    public Dialog getDialogByTwoUser(Long idUser1, Long idUser2);
}
