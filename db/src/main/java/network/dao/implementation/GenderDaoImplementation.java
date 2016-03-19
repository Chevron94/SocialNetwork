package network.dao.implementation;

import network.dao.GenderDao;
import network.entity.Gender;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roman on 9/23/15.
 */
public class GenderDaoImplementation extends GenericDaoImplementation<Gender,Long> implements GenderDao{
    public GenderDaoImplementation()
    {
        super(Gender.class);
    }

    @Override
    public Gender getGenderById(Long id) {
        String jpa = "SELECT g FROM Gender g WHERE g.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        List<Gender> genders = this.executeQuery(jpa, parameters);
        return genders.get(0);
    }
}
