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
        try{
            return albums.get(0);
        }catch (Exception e){
            return null;
        }

    }

    public Long getCountPhotosInAlbum(Long id){
        em = getEntityManager();
        String jpa = "Select count(p) From Photo p where p.album.id = :id";
        Long result = (Long) em.createQuery(jpa).setParameter("id", id).getSingleResult();
        closeEntityManager();
        return result;
    }

    public List<Album> getAlbumsByUserId(Long id, Integer start, Integer limit)
    {
        String jpa = "SELECT a FROM Album a WHERE a.user.id = :id order by a.creationDate desc, a.name";
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        return this.executeQuery(jpa, parameters,start, limit);
    }

    @Override
    public Album getAlbumByUserIdAndName(Long id, String name) {
        String jpa = "SELECT a FROM Album a WHERE a.user.id = :id AND a.name = :name";
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("id",id);
        parameters.put("name",name);
        List<Album> albums = this.executeQuery(jpa, parameters);
        try{
            return albums.get(0);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Album> getLatestAlbumsByUserId(Long id, Integer count) {
        String jpa = "SELECT a FROM Album a WHERE a.user.id = :id AND a.name <> 'Main' order by a.creationDate desc, a.name";
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("id",id);
        return this.executeQuery(jpa, parameters,0, count);
    }

    @Override
    public Long getNumberOfAlbumsByUserId(Long id) {
        String jpa = "SELECT Count(a) FROM Album a WHERE a.user.id = :id AND a.name <> 'Main' AND EXISTS(SELECT p FROM Photo p WHERE p.album.id = a.id)";
        em = getEntityManager();
        return (Long) em.createQuery(jpa).setParameter("id",id).getSingleResult();
    }
}
