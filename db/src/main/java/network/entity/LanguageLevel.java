package network.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by roman on 15.09.15.
 */
@Entity
@Table(name="LANGUAGE_LEVEL")
public class LanguageLevel {
    @Id
    @Column(name="ID_LANGUAGE_LEVEL")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="NAME")
    private String name;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "languageLevel")
    @JsonIgnore
    private transient Set<LanguageUser> languageUsers = new HashSet<LanguageUser>();



    public LanguageLevel() {
    }

    public LanguageLevel(String name) {
        this.name = name;
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

    public Set<LanguageUser> getLanguageUsers() {
        return languageUsers;
    }

    public void setLanguageUsers(Set<LanguageUser> languageUsers) {
        this.languageUsers = languageUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LanguageLevel that = (LanguageLevel) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "LanguageLevel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
