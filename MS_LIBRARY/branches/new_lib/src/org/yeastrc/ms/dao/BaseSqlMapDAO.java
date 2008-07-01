/**
 * BaseDAO.java
 * @author Vagisha Sharma
 * Jun 17, 2008
 * @version 1.0
 */
package org.yeastrc.ms.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;

public class BaseSqlMapDAO {
    
    protected final SqlMapClient sqlMap;
    
    protected static final Logger log = Logger.getLogger(BaseSqlMapDAO.class);
    
    public BaseSqlMapDAO(SqlMapClient sqlMap) {
        this.sqlMap = sqlMap;
    }
    
    /**
     * @param statementName
     * @param parameterObject
     * @return returns the object matching the query parameters; null if no matching object was found
     * @throws RuntimeException if query execution failed
     */
    public Object queryForObject(String statementName, Object parameterObject) {
        try {
            return sqlMap.queryForObject(statementName, parameterObject);
        }
        catch (SQLException e) {
            log.error("Failed to execute select statement", e);
            throw new RuntimeException("Failed to execute select statement", e);
        }
    }
    
    /**
     * @param statementName
     * @param parameterObject
     * @return returns a List of objects matching the query parameters.
     * @throws RuntimeException if query execution failed
     */
    public List queryForList(String statementName, Object parameterObject) {
        try {
            return sqlMap.queryForList(statementName, parameterObject);
        }
        catch (SQLException e) {
            log.error("Failed to execute select list statement", e);
            throw new RuntimeException("Failed to execute select list statement", e);
        }
    }
    
    /**
     * @param statementName
     * @return returns a List of objects matching the query parameters.
     * @throws RuntimeException if query execution failed
     */
    public List queryForList(String statementName) {
        try {
            return sqlMap.queryForList(statementName);
        }
        catch (SQLException e) {
            log.error("Failed to execute select list statement", e);
            throw new RuntimeException("Failed to execute select list statement", e);
        }
    }
    
    /**
     * @param statementName
     * @param parameterObject
     * @return returns the database id of the saved object.
     * @throws RuntimeException if query execution failed
     */
    public int saveAndReturnId(String statementName, Object parameterObject) {
        try {
            return (Integer) sqlMap.insert(statementName, parameterObject);
        }
        catch (SQLException e) {
            log.error("Failed to execute save statement", e);
            throw new RuntimeException("Failed to execute save statement", e);
        }
    }
    
    /**
     * @param statementName
     * @param parameterObject
     * @throws RuntimeException if query execution failed
     */
    public void save(String statementName, Object parameterObject) {
        try {
            sqlMap.insert(statementName, parameterObject);
        }
        catch (SQLException e) {
            log.error("Failed to execute save statement", e);
            throw new RuntimeException("Failed to execute save statement", e);
        }
    }
    
    /**
     * @param statementName
     * @param parameterObject
     * @throws RuntimeException if query execution failed
     */
    public void delete(String statementName, Object parameterObject) {
        try {
            sqlMap.delete(statementName, parameterObject);
        }
        catch (SQLException e) {
            log.error("Failed to execute delete statement", e);
            throw new RuntimeException("Failed to execute delete statement", e);
        }
    }
    
    /**
     * @param statementName
     * @throws RuntimeException if query execution failed
     */
    public void deleteAll(String statementName) {
        try {
            sqlMap.delete(statementName);
        }
        catch (SQLException e) {
            log.error("Failed to execute deleteAll statement", e);
            throw new RuntimeException("Failed to execute deleteAll statement", e);
        }
    }
}
