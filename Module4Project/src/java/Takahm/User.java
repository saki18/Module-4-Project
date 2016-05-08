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

import java.io.Serializable;
import java.util.Set;

/**
 * User class will create the user interface. 
 * @author Takahashi
 */
public class User implements Serializable, Comparable<User> {
    
    public static final String ADMINISTRATOR = "ADMIN";
    public static final String INVENTORY_MANAGER = "INV_MAN";

    private String username;
    private String password;
    private Set<String> roles;

    public boolean isAdministrator() {
        return roles.contains(ADMINISTRATOR);
    }
    
    public boolean isInventoryManager() {
        return roles.contains(INVENTORY_MANAGER) || isAdministrator();
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public int compareTo(User t) {
        return username.compareTo(t.getUsername());
    }

}
