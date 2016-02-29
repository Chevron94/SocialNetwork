package network.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by roman on 13.09.15.
 */
@Entity
@Table(name = "COUNTRY")
public class Country
{
    @Id
    @Column(name="ID_COUNTRY")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="NAME")
    private String name;

    @Column(name = "FLAG_URL")
    private String flagURL;

    @Column(name = "ISO")
    private String iso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CONTINENT", nullable = false)
    private Continent continent;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    @JsonIgnore
    private transient Set<City> cities = new HashSet<City>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    @JsonIgnore
    private transient Set<User> users = new HashSet<User>();



    public Country() {
    }

    public Country(String name, String flagURL, Continent continent) {
        this.name = name;
        this.flagURL = flagURL;
        this.continent = continent;
    }

    public String getFlagURL() {
        return flagURL;
    }

    public void setFlagURL(String flagURL) {
        this.flagURL = flagURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        return !(id != null ? !id.equals(country.id) : country.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", flagURL='" + flagURL + '\'' +
                ", continent=" + continent +
                '}';
    }
}
