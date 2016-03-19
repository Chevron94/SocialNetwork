package network.dao;

import network.entity.Continent;

import javax.ejb.Local;
import javax.ejb.Remote;

/**
 * Created by roman on 22.09.15.
 */
@Local
public interface ContinentDao extends GenericDao<Continent,Long> {
    public Continent getContinentById(Long id);
}
