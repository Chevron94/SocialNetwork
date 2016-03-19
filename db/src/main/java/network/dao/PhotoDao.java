package network.dao;

import network.entity.Photo;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
@Local
public interface PhotoDao extends GenericDao<Photo,Long> {

    public Photo getPhotoByID(Long id);
    public List<Photo> getPhotosByAlbumId(Long id, Integer start, Integer limit);
}
