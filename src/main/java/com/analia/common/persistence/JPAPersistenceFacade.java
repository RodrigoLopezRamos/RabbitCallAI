package com.analia.common.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.exception.PersistenceException;
import com.analia.common.model.AnaliaEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.Query;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Rodrigo Lopez
 */
public abstract class JPAPersistenceFacade<T extends AnaliaEntity> implements PersistenceFacade<T>, PanacheRepository<T> {
    protected String entity;
    private final Class<T> entityClass;

    public JPAPersistenceFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    // protected abstract EntityManager getEntityManager();

    /**
     *
     */
    public void save(T entity) throws AnaliaException {
        if (entity.getId() == null || BigInteger.ZERO.compareTo(entity.getId()) == 0) {
            getEntityManager().persist(entity);
        } else {
            getEntityManager().merge(entity);
        }

    }

    /**
     * {@inheritDocm PersistenceFacade}
     */
    public void remove(T entity) throws AnaliaException {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * {@inheritDocm PersistenceFacade}
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }


    public List<T> findAllJPa() {
        Query query = getEntityManager().createQuery("select x from " + getEntityName() + " x");
        return (List<T>) query.getResultList();
    }

    /**
     * @return
     */
    public String getEntityName() {
        if (entity == null) {
            Entity entityAnn = entityClass.getAnnotation(Entity.class);
            if (entityAnn != null && !entityAnn.name().equals("")) {
                entity = entityAnn.name();
            } else {
                entity = entityClass.getSimpleName();
            }
        }
        return entity;
    }

    /**
     * Create an instance of Query for executing a Java Persistence query
     * language statmanagerent.
     *
     * @param jpqlQuery
     * @return List<Object>
     */
    @SuppressWarnings("unchecked")
    public Collection<T> getListForJpqlQuery(String jpqlQuery) {
        Query query = getEntityManager().createQuery(jpqlQuery);
        return query.getResultList();
    }

    /**
     * Create an instance of Query for executing a named query (in the Java
     * Persistence query language or in native SQL).
     *
     * @param namedQuery
     * @return List<Object>
     */
    @SuppressWarnings("unchecked")
    public List<T> getListForNamedQuery(String namedQuery) {
        Query query = getEntityManager().createNamedQuery(namedQuery);
        return query.getResultList();
    }

    /**
     * Create an instance of Query for executing a native SQL query.
     *
     * @param sqlQuery
     * @param resultClass
     * @return List<Object>
     */
    @SuppressWarnings("unchecked")
    public Collection<T> getListForSQLQuery(String sqlQuery, Class<T> resultClass) {
        Query query = getEntityManager().createNativeQuery(sqlQuery, resultClass);
        return query.getResultList();
    }

    /**
     * Delete an entity from database;
     *
     * @param namedQuery
     * @return Object
     */
    public void deleteForNamedQuery(String tableName) {
        Query query = getEntityManager().createQuery("Delete from " + tableName);
        query.executeUpdate();
    }

    /**
     * Create an instance of Query for executing a named query (in the Java
     * Persistence query language or in native SQL).
     *
     * @param namedQuery
     * @return Object
     */
    @SuppressWarnings("unchecked")
    public T getPersistForNamedQuery(String namedQuery) {
        Query query = getEntityManager().createNamedQuery(namedQuery);
        query.setMaxResults(1); // We don't want to fetch rows that we won't use.
        List<T> r = query.getResultList();
        if (!r.isEmpty()) {
            return r.get(0);
        }
        return null;
    }

    /**
     * Create an instance of Query for executing a named query (in the Java
     * Persistence query language or in native SQL).
     *
     * @param namedQuery
     * @return Object
     */
    @SuppressWarnings("unchecked")
    public T getPersistForNamedQuery(String namedQuery, JpqlParameter... params) {
        Query query = getEntityManager().createNamedQuery(namedQuery);
        for (int i = 0; i < params.length; i++) {
            query.setParameter(params[i].getParameterName(), params[i].getParameterContent());
        }
        query.setMaxResults(1); // We don't want to fetch rows we won't use
        List<T> r = query.getResultList();
        if (!r.isEmpty()) {
            return r.get(0);
        }
        return null;
    }

    /**
     * Create an instance of Query for executing a named query (in the Java
     * Persistence query language or in native SQL).
     * <p>
     * variable "first" is numbered from 0
     *
     * @param namedQuery
     * @return Collection<Object>
     */
    @SuppressWarnings("unchecked")
    public List<T> getListForNamedQueryPaginationWithParameters(int first, int pageSize, String namedQuery, JpqlParameter... params) {
        Query query = getEntityManager().createNamedQuery(namedQuery);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);

        for (int i = 0; i < params.length; i++) {
            query.setParameter(params[i].getParameterName(), params[i].getParameterContent());
        }
        return query.getResultList();
    }

    /**
     * Create an instance of Query for executing a named query (in the Java
     * Persistence query language or in native SQL).
     *
     * @param namedQuery
     * @return Collection<Object>
     */
    @SuppressWarnings("unchecked")
    public List<T> getListForNamedQueryPagination(int first, int pageSize, String namedQuery) {
        Query query = getEntityManager().createNamedQuery(namedQuery);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    /**
     * Create an instance of Query for executing a named query (in the Java
     * Persistence query language or in native SQL).
     *
     * @param namedQuery
     * @return Collection<Object>
     */
    @SuppressWarnings("unchecked")
    public List<T> getListForNamedQuery(String namedQuery, JpqlParameter... params) {
        Query query = getEntityManager().createNamedQuery(namedQuery);
        for (int i = 0; i < params.length; i++) {
            query.setParameter(params[i].getParameterName(), params[i].getParameterContent());
        }
        return query.getResultList();
    }

    /**
     * Create an instance of Query for executing a named query (in the Java
     * Persistence query language or in native SQL).
     *
     * @param namedQuery
     * @param JpqlParameter ... param
     * @return Collection<Object>
     */
    @SuppressWarnings("unchecked")
    public Collection<T> getListForNamedQueryWithJpqlParameter(String namedQuery, JpqlParameter... params) {
        Query query = getEntityManager().createNamedQuery(namedQuery);

        for (int i = 0; i < params.length; i++) {
            query.setParameter(params[i].getParameterName(), params[i].getParameterContent());
        }
        return query.getResultList();
    }

    /**
     * Create an instance of Query for executing a named query (in the Java
     * Persistence query language or in native SQL).
     *
     * @param namedQuery
     * @return Collection<Object>
     */
    @SuppressWarnings("unchecked")
    public Collection<T> getRangeListForNamedQueryWithJpqlParameter(String namedQuery, int[] range, JpqlParameter... params) {
        Query query = getEntityManager().createNamedQuery(namedQuery);

        for (int i = 0; i < params.length; i++) {
            query.setParameter(params[i].getParameterName(), params[i].getParameterContent());
        }
        query.setMaxResults(range[1] - range[0]);
        query.setFirstResult(range[0]);
        List<T> ret = new ArrayList<T>();
        ret = query.getResultList();
        return ret;
    }

    /**
     * Create an instance of Query for executing a java persistence query (in the
     * Java Persistence query language or in native SQL).
     *
     * @param jpQuery
     * @param range   return Collection<Object>
     */
    @SuppressWarnings("unchecked")
    public Collection<T> getRangeListForQuery(String jpQuery, int[] range) {
        Query query = getEntityManager().createQuery(jpQuery);
        query.setMaxResults(range[1] - range[0]);
        query.setFirstResult(range[0]);

        List<T> ret = new ArrayList<T>();
        ret = query.getResultList();
        return ret;
    }

    /**
     * Find all into the range specified and named query
     *
     * @param namedQueryName
     * @param range
     * @return List<T>
     */
    @SuppressWarnings("unchecked")
    public List<T> findRange(String namedQueryName, int[] range) {
        Query q = getEntityManager().createNamedQuery(namedQueryName);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * @param namedQuery
     * @param params
     * @return
     */
    public int updateEntity(String namedQuery, JpqlParameter... params) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        for (int i = 0; i < params.length; i++) {
            q.setParameter(params[i].getParameterName(), params[i].getParameterContent());
        }
        return q.executeUpdate();
    }

    /**
     * @param namedQuery
     * @return
     */
    public int updateEntity(String namedQuery) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        return q.executeUpdate();
    }

    /**
     * @return
     */
    public Long countWithParameters(String namedQuery, JpqlParameter... params) {
        Query query = getEntityManager().createNamedQuery(namedQuery);
        for (int i = 0; i < params.length; i++) {
            query.setParameter(params[i].getParameterName(), params[i].getParameterContent());
        }
        return ((Number) query.getSingleResult()).longValue();
    }

    /**
     *
     */
    public void flush() {
        getEntityManager().flush();
    }

    /**
     *
     */
    public void clearSession() {
        getEntityManager().flush();
        getEntityManager().clear();
    }
}
