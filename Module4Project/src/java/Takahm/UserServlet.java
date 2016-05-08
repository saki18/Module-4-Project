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

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides web access to view and create products in the store inventory.
 *
 * @author Takahashi 
 */
@WebServlet("/users")
public class UserServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        if (this.getServletContext().getAttribute("userDao") == null) {
            this.getServletContext().setAttribute("userDao", DataAccessObjectFactory.getUserDao());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User cu = (User) req.getSession().getAttribute("currentUser");
        if (cu == null || !cu.isAdministrator()) {
            resp.sendRedirect("login.jsp");
        } else {
            DataAccessObject<User> userDao = (DataAccessObject) this.getServletContext().getAttribute("userDao");

            String button = req.getParameter("button");
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String[] roles = req.getParameterValues("roles");

            User u = new User();
            u.setUsername(username);
            u.setPassword(password);
            Set<String> rolesSet = new HashSet<>();
            if (roles != null) {
                rolesSet.addAll(Arrays.asList(roles));
            }
            u.setRoles(rolesSet);
            
            /** 
             *  throw that exception and provide them with that error message. 
             */
            switch (button) {
                case "Create": {
                    try {
                        userDao.create(u);
                    } catch (DataAccessException ex) {
                        Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                        req.setAttribute("error message", ex.getMessage());
                    }
                }
                break;
                case "Edit": {
                    try {
                        userDao.update(u);
                    } catch (DataAccessException ex) {
                        Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                        req.setAttribute("error message", ex.getMessage());
                    }
                }
                break;
                case "Delete": {
                    try {
                        userDao.delete(username);
                    } catch (DataAccessException ex) {
                        Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                        req.setAttribute("error message", ex.getMessage());

                    }
                }
                break;
            }

            resp.sendRedirect("users.jsp");
        }
    }

}
