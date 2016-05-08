package Takahm;

import java.io.IOException;
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
 * LoginServlet, creates and interface for the Login
 * @author Takahashi
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        DataAccessObject<User> userDao = DataAccessObjectFactory.getUserDao();
        try {
            if (userDao.read("admin") == null) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin");
                Set<String> roles = new HashSet<>();
                roles.add(User.ADMINISTRATOR);
                admin.setRoles(roles);
                userDao.create(admin);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * doPost will intercept on HTTP POST requests
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String logout = req.getParameter("logout");

        if (username != null && password != null) {
            User u = null;
            try {
                u = DataAccessObjectFactory.getUserDao().read(username);
            } catch (DataAccessException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            boolean success = false;
            
            if (u != null) {
                success = password.equals(u.getPassword());
                req.getSession().setAttribute("currentUser", u);
            }

            if (success) {
                resp.sendRedirect("index.jsp");
            }
            else {
                resp.sendRedirect("login.jsp?failed=true");
            }
        }
        else if ("true".equals(logout)) {
            req.getSession().removeAttribute("currentUser");
            resp.sendRedirect("index.jsp");
        }

    }

}
