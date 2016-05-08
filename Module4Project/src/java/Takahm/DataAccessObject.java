/*
 * Masaki Takahashi
 * CITP 290 - Advanced JAVA.
 * Module 4 Project 
 * May 7, 2016 
 * Abstract: In part of course completion for CITP 290, Advance Java. The following program 
 * will allow provide a secure log in, and allow for you to create users and a database for 
 * an inventory. 
 */
package Takahm;

import java.util.List;

public interface DataAccessObject<E>{

    
    /** Create will create a new entry, the following are the options are able 
    * to choose from. 
     * @param entity
     * @throws Takahm.DataAccessException
    */
    
    void create (E entity) throws DataAccessException; 
    E read (Object id) throws DataAccessException;
    List<E> readAll() throws DataAccessException; 
    void update (E Entity)throws DataAccessException; 
    void delete(Object id)throws DataAccessException; 
}
