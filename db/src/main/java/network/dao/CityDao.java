package network.dao;

import network.entity.City;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
@Local
public interface CityDao extends GenericDao<City,Long> {
    public String getName(City city);
    public City getCityById(Long id);
    public List<City> getCitiesByCountryId(Long id);
    public List<City> getCitiesByCountryIdAndPartOfCityName(Long id, String name);
}
