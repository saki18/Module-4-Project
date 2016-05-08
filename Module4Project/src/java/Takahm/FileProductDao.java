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
import edu.lcc.citp.utility.CollectionFileStorageUtility;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 * FileProductDAO creates the DAO for the objects that were created and 
 * also exceptions. 
 * @author Takahashi
 */
public class FileProductDao implements DataAccessObject<Product> {

    @Override
    public void create(Product entity) {
        List<Product> pList = readAll();

        if (read(entity.getUpc()) != null) {
            System.out.println("Product already exists.");
        } else {
            pList.add(entity);
            Collections.sort(pList);
            try {
                CollectionFileStorageUtility.save(pList, Product.class);
            } catch (IOException ex) {
                Logger.getLogger(FileProductDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Product read(Object id) {
        Product matchP = null;
        for (Product p : readAll()) {
            if (p.getUpc().equals(id)) {
                matchP = p;
            }
        }

        return matchP;
    }

    @Override
    public List<Product> readAll() {
        List<Product> pds = new ArrayList<>();
        try {
            pds.addAll(CollectionFileStorageUtility.load(Product.class));
        } catch (IOException ex) {
            Logger.getLogger(FileProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pds;
    }

    @Override
    public void update(Product entity) {
        List<Product> pList = readAll();

        Product matchP = null;
        for (Product pElement : pList) {
            if (pElement.getUpc().equals(entity.getUpc())) {
                matchP = pElement;
            }
        }

        if (matchP == null) {
            System.out.println("Product not found.");
        } else {
            if (entity.getLongDetails() != null) {
                matchP.setLongDetails(entity.getLongDetails());
            }
            if (entity.getPrice() != null) {
                matchP.setPrice(entity.getPrice());
            }
            if (entity.getShortDetails() != null) {
                matchP.setShortDetails(entity.getShortDetails());
            }
            if (entity.getStock() != null) {
                matchP.setStock(entity.getStock());
            }

            try {
                CollectionFileStorageUtility.save(pList, Product.class);
            } catch (IOException ex) {
                Logger.getLogger(FileProductDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(Object id) {
        List<Product> pList = readAll();

        Product matchP = null;
        for (Product p : pList) {
            if (p.getUpc().equals(id)) {
                matchP = p;
                break;
            }
        }

        if (matchP == null) {
            System.out.println("Product not found.");
        } else {
            pList.remove(matchP);
            try {
                CollectionFileStorageUtility.save(pList, Product.class);
            } catch (IOException ex) {
                Logger.getLogger(FileProductDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
