package network.dao;

import java.util.List;
/**
 * Created by roman on 15.09.15.
 */
public interface GenericDao<T, PK> {
    public T create(T t);
    public T read(PK id);
    public T update(T t);
    public void delete(PK id);
    public List<T> readAll();
    public void deleteSeveral(List<T> t);
}
