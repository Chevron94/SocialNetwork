package network.controllers;

import network.dao.AlbumDao;
import network.dao.PhotoDao;
import network.dao.UserDao;
import network.entity.Album;
import network.entity.Photo;
import network.helpers.FileHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static network.helpers.Constants.HOST_URL;

/**
 * Created by Роман on 31.03.2016.
 */
@Controller
public class AlbumController extends GenericController {
    @EJB
    UserDao userService;
    @EJB
    PhotoDao photoService;
    @EJB
    AlbumDao albumService;

    @RequestMapping(value = "/albums", method = RequestMethod.GET)
    public String getAllAlbums(HttpServletRequest request, Model model){
        Long idUser = getUserId(request);
        List<Album> albums = albumService.getAlbumsByUserId(idUser,0,5);
        for(Album a:albums){
            a.setPhotos(photoService.getPhotosByAlbumId(a.getId(),0,6));
        }
        model.addAttribute("albums",albums);
        model.addAttribute("requestUser",userService.getUserById(idUser));
        return "albums";
    }
    @RequestMapping(value = "/user{id}/albums", method = RequestMethod.GET)
    public String getAllAlbums(@PathVariable String id, Model model){
        Long idRequestUser = Long.valueOf(id);
        List<Album> albums = albumService.getAlbumsByUserId(idRequestUser,0,5);
        if (albums == null || albums.size()==0)
            return "404";
        for(Album a:albums){
            a.setPhotos(photoService.getPhotosByAlbumId(a.getId(),0,6));
        }
        model.addAttribute("albums",albums);
        model.addAttribute("requestUser",userService.getUserById(idRequestUser));
        return "albums";
    }
    //Getting photos by Album
    @RequestMapping(value = "/albums/{id}", method = RequestMethod.GET)
    public String getAlbum(@PathVariable("id") Long id, Model model){
        Album album = albumService.getAlbumById(id);
        if (album == null)
            return "404";
        model.addAttribute("album",album);
        return "album";
    }
    //AddingPhotos
    @RequestMapping(value = "/albums/{id}", method = RequestMethod.POST)
    public String uploadImages(@PathVariable("id") Long id, HttpServletRequest request, MultipartRequest multipartRequest, Model model){
        Long userId = (Long)(request.getSession().getAttribute("idUser"));
        Album album = albumService.getAlbumById(id);
        List<MultipartFile> files = multipartRequest.getFiles("files");
        for(MultipartFile file:files){
            Photo photo;
            try {
                photo = new Photo(HOST_URL+"images/" + FileHelper.imageInputStreamToString(file.getInputStream(),userId,album.getId()), new Date(), album);
                photoService.create(photo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("album",album);
        return "album";
    }
//JS METHODS
    //Load more albums
    @RequestMapping(value = "/albums/more", method = RequestMethod.GET)
    public @ResponseBody List<Album> loadMoreAlbums(
            @RequestParam(value = "idUser") Long idRequestUser,
            @RequestParam(value = "start") Integer start,
            @RequestParam(value = "count") Integer count){
        List<Album> albums = albumService.getAlbumsByUserId(idRequestUser,start,count);
        for(Album a:albums){
            a.setPhotos(photoService.getPhotosByAlbumId(a.getId(),0,6));
        }
        return albums;
    }
    // Create new album
    @RequestMapping(value = "/albums", method = RequestMethod.POST)
    public @ResponseBody
    Long newAlbum(@RequestParam(value = "idCreator") Long idCreator, @RequestParam(value = "name") String name, HttpServletRequest request){
        Long userId = (Long)(request.getSession().getAttribute("idUser"));
        if(!Objects.equals(userId, idCreator)){
            return null;
        }
        Album album = albumService.getAlbumByUserIdAndName(userId, StringUtils.replaceEach(name, new String[]{"&", "\"", "<", ">", "'", "/",}, new String[]{"&amp;", "&quot;", "&lt;", "&gt;", "&apos;", "&#x2F;"}));
        if (album!=null){
            return 0L;
        }
        album = new Album(name,userService.getUserById(userId), new Date());
        album = albumService.create(album);
        return album.getId();
    }
    //Delete album
    @RequestMapping(value = "/albums/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Boolean deleteAlbum(@PathVariable("id") Long id, HttpServletRequest request){
        Long userId = (Long)(request.getSession().getAttribute("idUser"));
        Album album = albumService.getAlbumById(id);
        Long idCreator = album.getUser().getId();
        if(Objects.equals(userId, idCreator)){
            albumService.delete(id);
            return true;
        }else {
            return null;
        }
    }

    @RequestMapping(value = "/albums/{id}/rename", method = RequestMethod.POST)
    public @ResponseBody
    Boolean renameAlbum(@PathVariable("id") Long id, @RequestParam("newName") String newName, HttpServletRequest request){
        Long userId = (Long)(request.getSession().getAttribute("idUser"));
        Album album = albumService.getAlbumById(id);
        Long idCreator = album.getUser().getId();
        if(!Objects.equals(userId, idCreator)){
            return null;
        }else try{
            if(albumService.getAlbumByUserIdAndName(id,StringUtils.replaceEach(newName, new String[]{"&", "\"", "<", ">", "'", "/",}, new String[]{"&amp;", "&quot;", "&lt;", "&gt;", "&apos;", "&#x2F;"})) == null){
                album.setName(newName);
                albumService.update(album);
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    //Load more photos
    @RequestMapping(value = "/photos", method = RequestMethod.GET)
    public @ResponseBody
    List<Photo> loadMorePhotos(@RequestParam(value = "idAlbum") Long idAlbum,@RequestParam(value = "start") Integer start,@RequestParam(value = "count") Integer count){
        return photoService.getPhotosByAlbumId(idAlbum,start,count);
    }
    //Get number photos in album
    @RequestMapping(value = "/albums/{id}/photosCnt", method = RequestMethod.GET)
    public @ResponseBody
    Long getCount(@PathVariable(value = "id") Long idAlbum){
        return albumService.getCountPhotosInAlbum(idAlbum);
    }

    //Delete album
    @RequestMapping(value = "/photos/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Boolean deletePhoto(@PathVariable("id") Long id, HttpServletRequest request){
        Long userId = (Long)(request.getSession().getAttribute("idUser"));
        Photo photo = photoService.getPhotoByID(id);
        Long idCreator = photo.getAlbum().getUser().getId();
        if(Objects.equals(userId, idCreator)){
            photoService.delete(id);
            return true;
        }else {
            return null;
        }
    }
}
