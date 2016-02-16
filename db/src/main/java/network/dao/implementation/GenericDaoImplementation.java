package network.dao.implementation;

import network.dao.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by roman on 22.09.15.
 */
public class GenericDaoImplementation<T,PK> extends AbstractDaoImplementation<T,PK> implements GenericDao<T,PK> {
    private Class<T> instance;

    public GenericDaoImplementation(Class<T> instance)
    {
        this.instance = instance;
    }

    public T create(T t) {
        em = getEntityManager();
        t = em.merge(t);
        closeEntityManager();
        return t;
    }

    public T read(PK id) {
        em  = getEntityManager();
        T t = em.find(instance, id);
        closeEntityManager();
        return t;
    }


    public List<T> readAll() {
        em  = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(instance);
        Root<T> rootEntry = cq.from(instance);
        CriteriaQuery<T> all = cq.select(rootEntry);
        List<T> t = em.createQuery(all).getResultList();
        closeEntityManager();
        return t;
    }

    public T update(T t) {
        em  = getEntityManager();
        t = em.merge(t);
        closeEntityManager();
        return t;
    }

    public void delete(PK id) {
        em  = getEntityManager();
        T t = em.find(instance, id);
        em.remove(t);
        closeEntityManager();
    }

    @Override
    protected EntityManager getEntityManager() {
        em = Singleton.CreateEntityManager();
        em.getTransaction().begin();
        return em;
    }

    @Override
    protected void closeEntityManager() {
        if (em.getTransaction().isActive()) em.getTransaction().commit();
        if (em != null && em.isOpen()) em.close();
    }

    public void delete(List<T> t) {
        em  = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<T> cq = cb.createCriteriaDelete(instance);
        Root<T> rootEntry = cq.from(instance);
        cq.where(rootEntry.in(t));
        em.createQuery(cq).executeUpdate();
        closeEntityManager();
    }
}
