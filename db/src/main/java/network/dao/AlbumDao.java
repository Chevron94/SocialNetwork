package network.dao;

import network.entity.Album;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
@Local
public interface AlbumDao extends GenericDao<Album,Long> {
    public Album getAlbumById(Long id);
    public Album getAlbumByUserIdAndName(Long id, String name);
    public Long getCountPhotosInAlbum(Long id);
    public List<Album> getAlbumsByUserId(Long id, Integer start, Integer limit);
    public List<Album> getLatestAlbumsByUserId(Long id, Integer count);
    public Long getNumberOfAlbumsByUserId(Long id);
}
