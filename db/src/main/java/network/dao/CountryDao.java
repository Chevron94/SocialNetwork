package network.dao;

import network.entity.Country;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
@Local
public interface CountryDao extends GenericDao<Country,Long> {
    public Country getCountryById(Long id);
    public Country getCountryByIso(String iso);
    public List<Country> getCountryByContinentId(Long id);
    public List<Country> getCountriesWithUsers();
}
