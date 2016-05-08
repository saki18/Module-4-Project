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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DatabaseUserDAO creates interface for user for USER DAO. 
 * @author Takahashi
 */
public class DatabaseUserDao implements DataAccessObject<User> {

    public static final String DERBY_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    public static final String STORE_DB_URL = "jdbc:derby://localhost:1527/store;create=true";
    public static final String USER_DDL = "CREATE TABLE \"USER\" (USERNAME VARCHAR(25), PASSWORD VARCHAR(500), ROLES VARCHAR(5000))";
    public static final String USER_CREATE_DML = "INSERT INTO \"USER\" (USERNAME, PASSWORD, ROLES) VALUES (?,?,?)";
    public static final String USER_READ_SQL = "SELECT USERNAME, PASSWORD, ROLES FROM \"USER\" WHERE USERNAME = ?";
    public static final String USER_READALL_SQL = "SELECT USERNAME, PASSWORD, ROLES FROM \"USER\"";
    public static final String USER_UPDATE_DML = "UPDATE \"USER\" SET PASSWORD=?, ROLES=? WHERE USERNAME = ?";
    public static final String USER_DELETE_DML = "DELETE FROM \"USER\" WHERE USERNAME = ?";

    public DatabaseUserDao() {

        try {
            Class.forName(DERBY_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseUserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (Connection con = DriverManager.getConnection(STORE_DB_URL)) {
            if (!con.getMetaData().getTables(null, null, "USER", null).next()) {
                con.prepareStatement(USER_DDL).execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String mergeRoles(Set<String> roles) {
        String roleString = "";
        for (String role: roles) {
            roleString += role + ",";
        }
        return roleString;
    }
    
    private Set<String> splitRoles(String roles) {
        Set<String> roleSet = new HashSet<>();
        for (String role: roles.split(",")) {
            roleSet.add(role);
        }
        return roleSet;
    }

    @Override
    public void create(User entity) {
        try (Connection con = DriverManager.getConnection(STORE_DB_URL)) {
            PreparedStatement stmt = con.prepareStatement(USER_CREATE_DML);
            stmt.setString(1, entity.getUsername());
            stmt.setString(2, entity.getPassword());
            stmt.setString(3, mergeRoles(entity.getRoles()));
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public User read(Object id) {
        User p = null;

        try (Connection con = DriverManager.getConnection(STORE_DB_URL)) {
            PreparedStatement stmt = con.prepareStatement(USER_READ_SQL);
            stmt.setString(1, (String) id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                p = new User();
                p.setUsername(rs.getString("USERNAME"));
                p.setPassword(rs.getString("PASSWORD"));
                p.setRoles(splitRoles(rs.getString("ROLES")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return p;
    }

    @Override
    public List<User> readAll() throws DataAccessException {
        List<User> pList = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(STORE_DB_URL)) {
            PreparedStatement stmt = con.prepareStatement(USER_READALL_SQL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User p = new User();
                p.setUsername(rs.getString("USERNAME"));
                p.setPassword(rs.getString("PASSWORD"));
                p.setRoles(splitRoles(rs.getString("ROLES")));
                pList.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new DataAccessException("HAHAHA!!!", null);

        //return pList;
    }

    @Override
    public void update(User entity) {
        User original = read(entity.getUsername());
        if (entity.getPassword() == null) {
            entity.setPassword(original.getPassword());
        }
        if (entity.getRoles() == null) {
            entity.setRoles(original.getRoles());
        }

        try (Connection con = DriverManager.getConnection(STORE_DB_URL)) {
            PreparedStatement stmt = con.prepareStatement(USER_UPDATE_DML);
            stmt.setString(1, entity.getPassword());
            stmt.setString(2, mergeRoles(entity.getRoles()));
            stmt.setString(3, entity.getUsername());
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Object id) {
        try (Connection con = DriverManager.getConnection(STORE_DB_URL)) {
            PreparedStatement stmt = con.prepareStatement(USER_DELETE_DML);
            stmt.setString(1, (String) id);
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
