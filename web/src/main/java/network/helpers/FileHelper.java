package network.helpers;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.UUID;

import static network.helpers.Constants.*;
/**
 * Created by Роман on 30.01.2017.
 */
public class FileHelper {

    public static String imageInputStreamToString(InputStream inputStream, Long userId, Long albumId) {
        File directory = new File(FILES_PATH+File.separator+userId+File.separator+albumId);
        if (!directory.exists()){
            if (!directory.mkdirs()){
                throw new RuntimeException("Failed to create directory "+directory.getPath());
            }
        }
        try {
            File file = File.createTempFile(UUID.randomUUID().toString(), ".jpg", directory);
            FileOutputStream out = new FileOutputStream(file);
            IOUtils.copy(inputStream, out);
            inputStream.close();
            out.close();
            return userId+"/"+albumId+"/"+file.getName();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static InputStream stringToInputStream(String path){
        try {
            return new FileInputStream(FILES_PATH+File.separator+path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
