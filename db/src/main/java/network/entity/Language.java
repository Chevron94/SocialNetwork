package network.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "CODE")
    private String code;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "language")
    @JsonIgnore
    private transient List<LanguageUser> languageUsers = new ArrayList<LanguageUser>();



    public Language() {
    }

    public Language(String name, String code) {
        this.name = name;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String pictureURL) {
        this.code = pictureURL;
    }

    public List<LanguageUser> getLanguageUsers() {
        return languageUsers;
    }

    public void setLanguageUsers(List<LanguageUser> languageUsers) {
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
                ", pictureURL='" + code + '\'' +
                '}';
    }
}
