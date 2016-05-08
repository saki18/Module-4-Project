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
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * sessionFilter will check for the sessions allows access to the web page. 
 * @author Takahashi
 */
@WebFilter("/*")
public class sessionFilter implements Filter {

    /** 
     * The init method will get called by the servlet. 
     * @param filterConfig
     * @throws ServletException 
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    /**
     * the following doFilter causes the next filter in the chain to be invoked.  
     * this will be processed for each request.  
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {

            HttpServletRequest httpReq = (HttpServletRequest) request;
            String pageName = httpReq.getRequestURI();
            HttpServletResponse httpRes = (HttpServletResponse) response;
            HttpSession httpSession = httpReq.getSession();
            if (httpSession.getAttribute("currentUser") == null && !pageName.contains("login")) {
                httpRes.sendRedirect("login.jsp");
            }
            else {
               chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
