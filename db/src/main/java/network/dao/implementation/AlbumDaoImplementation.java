package network.dao.implementation;

import network.dao.AlbumDao;
import network.entity.Album;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
public class AlbumDaoImplementation extends GenericDaoImplementation<Album,Long> implements AlbumDao {
    public AlbumDaoImplementation()
    {
        super(Album.class);
    }
    public Album getAlbumById(Long id)
    {
        String jpa = "SELECT a FROM Album a WHERE a.id = :id";
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        List<Album> albums = this.executeQuery(jpa, parameters);
        return albums.get(0);
    }
    public List<Album> getAlbumsByUserId(Long id, Integer start, Integer limit)
    {
        String jpa = "SELECT a FROM Album a WHERE a.user.id = :id";
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        return this.executeQuery(jpa, parameters,start, limit);
    }

}
