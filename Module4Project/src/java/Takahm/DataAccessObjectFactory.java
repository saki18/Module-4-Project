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

import edu.lcc.citp.inventory.Product;

/**
 * DataAccessObjectFactory will utilize DataAccessObject which gets the information 
 * for the user and product DAO. 
 * @author Takahashi
 */
public class DataAccessObjectFactory {
  
    public static DataAccessObject<User> getUserDao() {
        return new DatabaseUserDao();
    }
    
    public static DataAccessObject<Product> getProductDao() {
        return new DatabaseProductDao();
    }
}
