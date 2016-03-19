package network.dao.implementation;

import network.dao.FileDao;
import network.entity.File;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
public class FIleDaoImplementation extends GenericDaoImplementation<File,Long> implements FileDao {
    public FIleDaoImplementation()
    {
        super(File.class);
    }


    public File getFileById(Long id) {
        String jpa = "SELECT f FROM File f WHERE f.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        List<File> files = this.executeQuery(jpa, parameters);
        return files.get(0);
    }

    public List<File> getFilesByMessageId(Long id) {
        String jpa = "SELECT f FROM File f WHERE f.message.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        return this.executeQuery(jpa, parameters);
    }
}
