package network.dao;

import network.entity.File;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
@Local
public interface FileDao extends GenericDao<File,Long> {
    public String getFileUrl(File file);
    public Long getFileSize(File file);
    public String getFileName(File file);

    public File getFileById(Long id); 
    public List<File> getFilesByMessageId(Long id);
}
