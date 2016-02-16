package network.entity;

import javax.persistence.*;

/**
 * Created by roman on 22.09.15.
 */
@Entity
@Table(name = "PHOTO")
public class Photo {
    @Id
    @Column(name = "ID_PHOTO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PHOTO_URL")
    private String photoUrl;

    @Column(name = "IS_MAIN")
    private boolean isMain;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ALBUM", nullable = false)
    private Album album;

    public Photo() {
    }

    public Photo(String photoUrl, boolean isMain, Album album) {
        this.photoUrl = photoUrl;
        this.isMain = isMain;
        this.album = album;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setIsMain(boolean isMain) {
        this.isMain = isMain;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        return !(id != null ? !id.equals(photo.id) : photo.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", photoUrl='" + photoUrl + '\'' +
                ", isMain=" + isMain +
                ", album=" + album +
                '}';
    }
}
