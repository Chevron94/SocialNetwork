package network.dao.implementation;

import network.dao.PhotoDao;
import network.entity.Photo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
public class PhotoDaoImplementation extends GenericDaoImplementation<Photo,Long> implements PhotoDao {
    public PhotoDaoImplementation()
    {
        super(Photo.class);
    }

    public Photo getPhotoByID(Long id) {
        String jpa = "SELECT p FROM Photo p WHERE p.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        List<Photo> messages = this.executeQuery(jpa, parameters);
        return messages.get(0);
    }

    public List<Photo> getPhotosByAlbumId(Long id, Integer start, Integer limit) {
        String jpa = "SELECT p FROM Photo p WHERE p.album.id = :id";
        HashMap<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        return this.executeQuery(jpa, parameters, start, limit);
    }
}
