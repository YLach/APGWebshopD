/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.security;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import webshop.view.LoginManager;
import webshop.view.Navigation;

/**
 * AuthorizationFilter class used to protect resources and check client access.
 */
@WebFilter("*.xhtml")
public class AuthorizationFilter implements Filter {

    @Inject
    private LoginManager loginBean;

    public AuthorizationFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Nothing to do
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest reqt = (HttpServletRequest) request;
        HttpServletResponse rsp = (HttpServletResponse) response;
        String adminURL = reqt.getContextPath() + "/" + Navigation.ADMIN_URL;
        String loginURL = reqt.getContextPath() + "/" + Navigation.toLogin();
        String inventoryURL = reqt.getContextPath() + "/faces/" + Navigation.redirectToInventory();
        String inventoryAdminURL = reqt.getContextPath() + "/" + Navigation.redirectToInventoryAdmin();

        if (reqt.getRequestURI().contains("login.xhtml") && userLoggedIn()) {
            // Try to access login page but already authenticated
            String redirectPage = customerLoggedIn() ? inventoryURL : inventoryAdminURL;
            rsp.sendRedirect(inventoryURL);
        } else if (adminLoggedIn()
                && !reqt.getRequestURI().contains(adminURL)) {
            // Try to access customer webpage as admin
            rsp.sendRedirect(inventoryAdminURL);
        } else if (customerLoggedIn() && reqt.getRequestURI().contains(adminURL)) {
            // Try to access admin webpage without being admin
            rsp.sendRedirect(inventoryURL);
        } else if (!reqt.getRequestURI().equals(loginURL)
                && !userLoggedIn()) {
            // Try to access webpage without being authenticated
            rsp.sendRedirect(loginURL);
        } else if (customerLoggedIn() && userBanned()) {
            // User banned
            if (loginBean != null) {
                loginBean.setIsLoggedIn(false);
            }
            rsp.sendRedirect(loginURL);
        } else {
            // Otherwise = normal access
            chain.doFilter(request, response);
        }
    }

    // User = Admin or customer
    private boolean userLoggedIn() {
        return (loginBean != null
                && (loginBean.isLoggedIn() || loginBean.isAdmin()));
    }

    private boolean customerLoggedIn() {
        return (loginBean != null && loginBean.isLoggedIn());
    }

    private boolean adminLoggedIn() {
        return (loginBean != null && loginBean.isAdmin());
    }

    private boolean userBanned() {
        return (loginBean != null && loginBean.isBanned());
    }

    @Override
    public void destroy() {
        //Nothing to do
    }
}
