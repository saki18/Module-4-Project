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

import java.sql.SQLException;

/**
 * DataAccessException class will extend Exception in which it will throw exceptions. 
 * @author Takahashi
 */
class DataAccessException extends Exception {

    DataAccessException(String other_SQLException, SQLException ex) {
        super (other_SQLException, ex);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    DataAccessException(DataAccessException ex) {
        super (ex); 
    }
    
}
