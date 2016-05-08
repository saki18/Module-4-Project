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
import java.io.IOException;
import java.math.BigDecimal;
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
@WebServlet("/inventory")
public class InventoryServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        if (this.getServletContext().getAttribute("productDao") == null) {
            this.getServletContext().setAttribute("productDao", DataAccessObjectFactory.getProductDao());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User cu = (User) req.getSession().getAttribute("currentUser");
        if (cu == null || !cu.isInventoryManager()) {
            resp.sendRedirect("login.jsp");
        } else {
            DataAccessObject<Product> productDao = (DataAccessObject) this.getServletContext().getAttribute("productDao");

            String button = req.getParameter("button");
            String upc = req.getParameter("upc");
            String shortDetails = req.getParameter("shortDetails");
            String longDetails = req.getParameter("longDetails");
            String price = req.getParameter("price");
            String stock = req.getParameter("stock");

            Product p = new Product();
            p.setUpc(upc);
            p.setShortDetails(shortDetails);
            p.setLongDetails(longDetails);
            if (price != null) {
                p.setPrice(new BigDecimal(price));
            }
            if (stock != null) {
                p.setStock(Integer.parseInt(stock));
            }

            switch (button) {
                case "Create":
            {
                try {
                    productDao.create(p);
                } catch (DataAccessException ex) {
                    Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                    break;
                case "Edit":
            {
                try {
                    productDao.update(p);
                } catch (DataAccessException ex) {
                    Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                    break;
                case "Delete":
            {
                try {
                    productDao.delete(upc);
                } catch (DataAccessException ex) {
                    Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                    break;
            }

            resp.sendRedirect("inventory.jsp");
        }

    }
}
