package network.controllers;

import network.helpers.FileHelper;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by Роман on 30.01.2017.
 */
@Controller
public class FileController {
    @RequestMapping(value = "/images/{userid}/{album}/{photo:.+}", method = RequestMethod.GET)
    public void getFile(@PathVariable("userid") String userid, @PathVariable("album") String album, @PathVariable("photo") String photo, HttpServletResponse response) {
        try {
            InputStream inputStream = FileHelper.stringToInputStream(userid+File.separator+album+File.separator+photo);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
