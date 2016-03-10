package network.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by roman on 22.09.15.
 */
@Entity
@Table(name="DIALOG")
public class Dialog {
    @Id
    @Column(name = "ID_DIALOG")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name="LAST_MESSAGE_DATETIME")
    private Date lastMessageDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dialog")
    @JsonIgnore
    private transient Set<Message> messages = new HashSet<Message>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dialog")
    @JsonIgnore
    private transient Set<UserDialog> userDialogs = new HashSet<UserDialog>();



    public Dialog() {
    }

    public Dialog(String name) {
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

    public Date getLastMessageDate() {
        return lastMessageDate;
    }

    public void setLastMessageDate(Date lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dialog dialog = (Dialog) o;

        return !(id != null ? !id.equals(dialog.id) : dialog.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Dialog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
