package network.dao;

import network.entity.Album;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
@Local
public interface AlbumDao extends GenericDao<Album,Long> {
    public String getName(Album album);
    public Album getAlbumById(Long id);
    public List<Album> getAlbumsByUserId(Long id);
}
