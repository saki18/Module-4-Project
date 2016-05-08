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

import edu.lcc.citp.utility.CollectionFileStorageUtility;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FileUserDAO creates the DAO for user database. 
 * @author Takahashi
 */
public class FileUserDao implements DataAccessObject<User> {
    
    @Override
    public void create(User entity) {
        List<User> pList = readAll();

        if (read(entity.getUsername()) != null) {
            System.out.println("User already exists.");
        } else {
            pList.add(entity);
            Collections.sort(pList);
            try {
                CollectionFileStorageUtility.save(pList, User.class);
            } catch (IOException ex) {
                Logger.getLogger(FileUserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public User read(Object id) {
        User matchP = null;
        for (User p : readAll()) {
            if (p.getUsername().equals(id)) {
                matchP = p;
            }
        }

        return matchP;
    }

    @Override
    public List<User> readAll() {
        List<User> pds = new ArrayList<>();
        try {
            pds.addAll(CollectionFileStorageUtility.load(User.class));
        } catch (IOException ex) {
            Logger.getLogger(FileUserDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileUserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pds;
    }

    @Override
    public void update(User entity) {
        List<User> pList = readAll();

        User matchP = null;
        for (User pElement : pList) {
            if (pElement.getUsername().equals(entity.getUsername())) {
                matchP = pElement;
            }
        }

        if (matchP == null) {
            System.out.println("User not found.");
        } else {
            if (entity.getPassword() != null) {
                matchP.setPassword(entity.getPassword());
            }
            if (entity.getRoles() != null) {
                matchP.setRoles(entity.getRoles());
            }

            try {
                CollectionFileStorageUtility.save(pList, User.class);
            } catch (IOException ex) {
                Logger.getLogger(FileUserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(Object id) {
        List<User> pList = readAll();

        User matchP = null;
        for (User p : pList) {
            if (p.getUsername().equals(id)) {
                matchP = p;
                break;
            }
        }

        if (matchP == null) {
            System.out.println("User not found.");
        } else {
            pList.remove(matchP);
            try {
                CollectionFileStorageUtility.save(pList, User.class);
            } catch (IOException ex) {
                Logger.getLogger(FileUserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
