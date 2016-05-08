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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DatabaseProductDAO  class implements DataAccesObject Product this is used to make a web interface. 
 * @author Takahashi
 */
public class DatabaseProductDao implements DataAccessObject<Product> {

    public static final String DERBY_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    public static final String STORE_DB_URL = "jdbc:derby://localhost:1527/store;create=true";
    public static final String PRODUCT_DDL = "CREATE TABLE PRODUCT (UPC VARCHAR(25), SHORT_DETAILS VARCHAR(50), LONG_DETAILS VARCHAR(5000), PRICE DECIMAL(10,2), STOCK INTEGER, PRIMARY KEY (UPC))";
    public static final String PRODUCT_CREATE_DML = "INSERT INTO PRODUCT (UPC, SHORT_DETAILS, LONG_DETAILS, PRICE, STOCK) VALUES (?,?,?,?,?)";
    public static final String PRODUCT_READ_SQL = "SELECT UPC, SHORT_DETAILS, LONG_DETAILS, PRICE, STOCK FROM PRODUCT WHERE UPC = ?";
    public static final String PRODUCT_READALL_SQL = "SELECT UPC, SHORT_DETAILS, LONG_DETAILS, PRICE, STOCK FROM PRODUCT";
    public static final String PRODUCT_UPDATE_DML = "UPDATE PRODUCT SET SHORT_DETAILS=?, LONG_DETAILS=?, PRICE=?, STOCK=? WHERE UPC = ?";
    public static final String PRODUCT_DELETE_DML = "DELETE FROM PRODUCT WHERE UPC = ?";

    /** 
     * DatabaseProductDAO will throw exceptions. 
     */
    public DatabaseProductDao() {

        try {
            Class.forName(DERBY_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (Connection con = DriverManager.getConnection(STORE_DB_URL)) {
            if (!con.getMetaData().getTables(null, null, "PRODUCT", null).next()) {
                con.prepareStatement(PRODUCT_DDL).execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void create(Product entity) {
        try (Connection con = DriverManager.getConnection(STORE_DB_URL)) {
            PreparedStatement stmt = con.prepareStatement(PRODUCT_CREATE_DML);
            stmt.setString(1, entity.getUpc());
            stmt.setString(2, entity.getShortDetails());
            stmt.setString(3, entity.getLongDetails());
            stmt.setBigDecimal(4, entity.getPrice());
            stmt.setInt(5, entity.getStock());
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Product read(Object id) throws DataAccessException {
        Product p = null;

        try (Connection con = DriverManager.getConnection(STORE_DB_URL)) {
            PreparedStatement stmt = con.prepareStatement(PRODUCT_READ_SQL);
            stmt.setString(1, (String) id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                p = new Product();
                p.setUpc(rs.getString("UPC"));
                p.setShortDetails(rs.getString("SHORT_DETAILS"));
                p.setLongDetails(rs.getString("LONG_DETAILS"));
                p.setPrice(rs.getBigDecimal("PRICE"));
                p.setStock(rs.getInt("STOCK"));
            }
        } catch (SQLException ex)
            
        {   Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex); 
            throw new DataAccessException ("Other SQLException", ex);
  
        }

        return p;
    }

    @Override
    public List<Product> readAll() {
        List<Product> pList = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(STORE_DB_URL)) {
            PreparedStatement stmt = con.prepareStatement(PRODUCT_READALL_SQL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setUpc(rs.getString("UPC"));
                p.setShortDetails(rs.getString("SHORT_DETAILS"));
                p.setLongDetails(rs.getString("LONG_DETAILS"));
                p.setPrice(rs.getBigDecimal("PRICE"));
                p.setStock(rs.getInt("STOCK"));
                pList.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pList;
    }

    @Override
    public void update(Product entity) throws DataAccessException {
        Product original = read(entity.getUpc());
        if (entity.getShortDetails() == null) {
            entity.setShortDetails(original.getShortDetails());
        }
        if (entity.getLongDetails() == null) {
            entity.setLongDetails(original.getLongDetails());
        }
        if (entity.getPrice() == null) {
            entity.setPrice(original.getPrice());
        }
        if (entity.getStock() == null) {
            entity.setStock(original.getStock());
        }
        try (Connection con = DriverManager.getConnection(STORE_DB_URL)) {
            PreparedStatement stmt = con.prepareStatement(PRODUCT_UPDATE_DML);
            stmt.setString(1, entity.getShortDetails());
            stmt.setString(2, entity.getLongDetails());
            stmt.setBigDecimal(3, entity.getPrice());
            stmt.setInt(4, entity.getStock());
            stmt.setString(5, entity.getUpc());
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Object id) {
        try (Connection con = DriverManager.getConnection(STORE_DB_URL)) {
            PreparedStatement stmt = con.prepareStatement(PRODUCT_DELETE_DML);
            stmt.setString(1, (String) id);
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
