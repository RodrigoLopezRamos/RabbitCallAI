package com.analia.common.persistence;

import com.analia.common.exception.AnaliaException;

import java.util.Collection;
import java.util.List;

public interface PersistenceFacade<T> {
    /**
     * @param entity
     */
    void save(T entity) throws AnaliaException;

    /**
     * @param entity
     */
    void remove(T entity) throws AnaliaException;

    /**
     * @param id
     * @return
     */
    T find(Object id);

    /**
     * @return
     */
    List<T> findAllJPa();

    /**
     * Create an instance of Query for executing a Java Persistence query
     * language statmanagerent.
     *
     * @param jpqlQuery
     * @return List<T>
     */
    Collection<T> getListForJpqlQuery(String jpqlQuery);

    /**
     * Create an instance of Query for executing a named query (in the Java
     * Persistence query language or in native SQL).
     *
     * @param namedQuery
     * @return List<T>
     */
    List<T> getListForNamedQuery(String namedQuery);

    /**
     * Create an instance of Query for executing a native SQL query.
     *
     * @param sqlQuery
     * @param resultClass
     * @return List<Object>
     */
    Collection<T> getListForSQLQuery(String sqlQuery, Class<T> resultClass);

    /**
     * Delete an entity from database;
     *
     * @param namedQuery
     * @return Object
     */
    void deleteForNamedQuery(String tableName);

    /**
     * Create an instance of Query for executing a named query (in the Java
     * Persistence query language or in native SQL).
     *
     * @param namedQuery
     * @return Object
     */
    T getPersistForNamedQuery(String namedQuery);

    /**
     * Create an instance of Query for executing a named query (in the Java
     * Persistence query language or in native SQL).
     *
     * @param namedQuery
     * @return Object
     */
    T getPersistForNamedQuery(String namedQuery, JpqlParameter... params);

    /**
     * Create an instance of Query for executing a named query (in the Java
     * Persistence query language or in native SQL).
     *
     * @param namedQuery
     * @param Object     ... param Parameters list in named query sequence (order)
     * @return Collection<Object>
     */
    List<T> getListForNamedQueryPaginationWithParameters(int first, int pageSize, String namedQuery, JpqlParameter... params);

    /**
     * Create an instance of Query for executing a named query (in the Java
     * Persistence query language or in native SQL).
     *
     * @param namedQuery
     * @param Object     ... param Parameters list in named query sequence (order)
     * @return Collection<Object>
     */
    List<T> getListForNamedQueryPagination(int first, int pageSize, String namedQuery);

    /**
     * Create an instance of Query for executing a named query (in the Java
     * Persistence query language or in native SQL).
     *
     * @param namedQuery
     * @param Object     ... param Parameters list in named query sequence (order)
     * @return Collection<Object>
     */
    List<T> getListForNamedQuery(String namedQuery, JpqlParameter... params);

    /**
     * Create an instance of Query for executing a named query (in the Java
     * Persistence query language or in native SQL).
     *
     * @param namedQuery
     * @param JpqlParameter ... param
     * @return Collection<Object>
     */
    Collection<T> getListForNamedQueryWithJpqlParameter(String namedQuery, JpqlParameter... params);

    /**
     * Create an instance of Query for executing a named query (in the Java
     * Persistence query language or in native SQL).
     *
     * @param namedQuery
     * @param JpqlParameter ... param
     * @return Collection<Object>
     */
    Collection<T> getRangeListForNamedQueryWithJpqlParameter(String namedQuery, int[] range, JpqlParameter... params);

    /**
     * Create an instance of Query for executing a java persistence query (in the
     * Java Persistence query language or in native SQL).
     *
     * @param jpQuery
     * @param range   return Collection<Object>
     */
    Collection<T> getRangeListForQuery(String jpQuery, int[] range);

    /**
     * Find all into the range especified and named query
     *
     * @param namedQueryName
     * @param range
     * @return List<Object>
     */
    List<T> findRange(String namedQueryName, int[] range);

    /**
     * @param namedQueryName
     * @param params
     * @return
     */
    Long countWithParameters(String namedQueryName, JpqlParameter... params);

    /**
     * to make flush to the database.
     */
    void flush();

    /**
     *
     */
    void clearSession();

}
