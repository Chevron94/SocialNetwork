package network.dao;

import network.entity.Gender;

import javax.ejb.Local;
import javax.ejb.Remote;

/**
 * Created by roman on 9/23/15.
 */
@Local
public interface GenderDao extends GenericDao<Gender,Long>{
    public Gender getGenderById(Long id);


}
