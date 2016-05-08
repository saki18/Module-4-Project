/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Takahm;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * My LittleServletContextListener DAO beans are automatically created as application
 * attributes when your application starts.
 * @author Takahashi
 */
@WebListener("/*")
public class MyLittleServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (sce.getServletContext().getAttribute ("productDAO")== null) {
            sce.getServletContext().setAttribute ("productDAO", DataAccessObjectFactory.getProductDao());} 
        
            if (sce.getServletContext().getAttribute ("userDAO")== null) {
            sce.getServletContext().setAttribute ("userDAO", DataAccessObjectFactory.getUserDao());}
    }    

        @Override
        public void contextDestroyed (ServletContextEvent sce){
        }
        }
