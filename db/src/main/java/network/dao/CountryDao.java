package network.dao;

import network.entity.Country;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
@Local
public interface CountryDao extends GenericDao<Country,Long> {
    public String getName(Country country);
    public String getFlagUrl(Country country);
    public Country getCountryById(Long id);
    public List<Country> getCountryByContinentId(Long id);
}
