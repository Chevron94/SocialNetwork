package network.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by roman on 13.09.15.
 */
@Entity
@Table(name = "NETWORK_USER", uniqueConstraints = @UniqueConstraint(columnNames = "LOGIN"))
public class User {
    @Id
    @Column(name = "ID_USER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LOGIN")

    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DATE_OF_BIRTHDAY")
    private Date birthday;

    @Column(name = "PHOTO_URL")
    private String photoURL;

    @Column(name = "CONFIRMED")
    private Boolean confirmed;

    @Column(name="ROLE")
    private int role;

    @Column(name = "IS_LOCKED")
    private Boolean isLocked;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Column(name = "TOKEN", nullable = true)
    private String token;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CITY", nullable = false)
    private City city;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_COUNTRY", nullable = false)
    private Country country;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_GENDER", nullable = false)
    private Gender gender;

    

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    @JsonIgnore
    private transient Set<FriendRequest> senders = new HashSet<FriendRequest>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "receiver")
    @JsonIgnore
    private transient Set<FriendRequest> receivers = new HashSet<FriendRequest>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private transient Set<LanguageUser> languageUsers = new HashSet<LanguageUser>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private transient Set<Message> messages = new HashSet<Message>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private transient Set<UserDialog> userDialogs = new HashSet<UserDialog>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private transient Set<Album> albums = new HashSet<Album>();



    public User() {
    }

    public User(String name, String login, String password, String email, Date birthday, String photoURL, City city, Gender gender) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.photoURL = photoURL;
        this.city = city;
        this.gender = gender;
        this.isLocked = false;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Set<LanguageUser> getLanguageUsers() {
        return languageUsers;
    }

    public void setLanguageUsers(Set<LanguageUser> languageUsers) {
        this.languageUsers = languageUsers;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Set<FriendRequest> getSenders() {
        return senders;
    }

    public void setSenders(Set<FriendRequest> senders) {
        this.senders = senders;
    }

    public Set<FriendRequest> getReceivers() {
        return receivers;
    }

    public void setReceivers(Set<FriendRequest> receivers) {
        this.receivers = receivers;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Set<UserDialog> getUserDialogs() {
        return userDialogs;
    }

    public void setUserDialogs(Set<UserDialog> userDialogs) {
        this.userDialogs = userDialogs;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return !(id != null ? !id.equals(user.id) : user.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", photoURL='" + photoURL + '\'' +
                ", confirmed=" + confirmed +
                ", role=" + role +
                ", isLocked=" + isLocked +
                ", city=" + city +
                ", country=" + country +
                ", gender=" + gender +
                '}';
    }
}
