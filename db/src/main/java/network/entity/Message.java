package network.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by roman on 14.09.15.
 */
@Entity
@Table(name = "MESSAGE")
public class Message {
    @Id
    @Column(name = "ID_MESSAGE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_USER", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_DIALOG", nullable = false)
    private Dialog dialog;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "DATETIME")
    private Date dateTime;

    @Column(name = "IS_READ")
    private boolean isRead;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "message")
    @JsonIgnore
    private transient Set<File> files = new HashSet<File>();



    public Message() {
    }

    public Message(User from, Dialog to, String text, Date dateTime, boolean isRead) {
        this.user = from;
        this.dialog = to;
        this.text = text;
        this.dateTime = dateTime;
        this.isRead = isRead;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return !(id != null ? !id.equals(message.id) : message.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", user=" + user +
                ", dialog=" + dialog +
                ", text='" + text + '\'' +
                ", dateTime=" + dateTime +
                ", isRead=" + isRead +
                '}';
    }
}
