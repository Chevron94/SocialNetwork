package network.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by roman on 14.09.15.
 */
@Entity
@Table(name = "LANGUAGE")
public class Language {
    @Id
    @Column(name="ID_LANGUAGE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="NAME")
    private String name;

    @Column(name = "PICTURE_URL")
    private String pictureURL;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "language")
    @JsonIgnore
    private transient Set<LanguageUser> languageUsers = new HashSet<LanguageUser>();



    public Language() {
    }

    public Language(String name, String pictureURL) {
        this.name = name;
        this.pictureURL = pictureURL;
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

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
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

        Language language = (Language) o;

        return !(id != null ? !id.equals(language.id) : language.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pictureURL='" + pictureURL + '\'' +
                '}';
    }
}
