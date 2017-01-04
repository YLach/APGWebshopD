/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.view;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import webshop.controller.BannedUserException;
import webshop.controller.InvalidCustomerException;
import webshop.controller.WebshopController;

/**
 * LoginManager is a managed bean with a SessionScoped scope and is associated
 * with the views dealing with users login.
 */
@Named(value = "loginManager")
@SessionScoped
public class LoginManager implements Serializable {

    private static final long serialVersionUID = 16247164401L;

    @EJB
    private WebshopController webshopController;

    private String username;
    private String password;
    private boolean isLoggedIn; // If customer user logged in
    private boolean isAdmin; // If admin user logged in

    //private boolean errorTransaction;
    /**
     * Creates a new instance of LoginManager.
     */
    public LoginManager() {
        this.isLoggedIn = false;
        this.isAdmin = false;
        //this.errorTransaction = false;
    }

    /**
     * Tries to registered a new customer
     *
     * @return The next web page to be displayed
     */
    public String doRegistration() {
        try {
            webshopController.addCustomer(username, password);
            // Set registration message
            FacesMessage msg = new FacesMessage("Registration successful!",
                    "INFO MSG");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (InvalidCustomerException ex) {
            //Display error message
            FacesMessage errorMessage = new FacesMessage("Registration "
                    + "failed: username already used");
            errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, errorMessage);
        }
        return Navigation.toLogin();
    }

    /**
     * Tries to authenticate the customer user trying to log in.
     *
     * @return The next web page to be displayed.
     */
    public String doLoginCustomer() {
        //TODO improve error handling 
        try {
            if (webshopController.validateCustomerLogin(username, password)) {
                isLoggedIn = true;
                return Navigation.redirectToInventory();
            } else {
                //Display error message
                FacesMessage errorMessage = new FacesMessage("Authentication "
                        + "failed: invalid username and/or password");
                errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, errorMessage);
            }
        } catch (BannedUserException ex) {
            FacesMessage errorMessage
                    = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            ex.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, errorMessage);
        }
        return Navigation.toLogin();
    }

    /**
     * Tries to authenticate the admin user trying to log in.
     *
     * @return The next web page to be displayed.
     */
    public String doLoginAdmin() {
        if (webshopController.validateAdminLogin(username, password)) {
            isAdmin = true;
            return Navigation.redirectToInventoryAdmin();
        } else {
            //Display error message
            FacesMessage errorMessage = new FacesMessage("Authentication "
                    + "failed: invalid username and/or password");
            errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, errorMessage);
        }
        return Navigation.toLogin();
    }

    /**
     * Logs out the currently logged user.
     */
    public void doLogout() {
        isLoggedIn = false;
        isAdmin = false;

        // Remove the current session
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        // Set logout message
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Logout successful !", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(Navigation.toLogin());
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns true if the logged in is banned, false otherwise.
     *
     * @return true if user banned.
     */
    public boolean isBanned() {
        return webshopController.isBanned(username);
    }

    /*
    public boolean success() {
        boolean returnValue = this.errorTransaction;
        this.errorTransaction = false;
        return returnValue ? false : true;        
    }
     */

    /*************************************************************************
     *                          GETTERS and SETTERS                          *
     *************************************************************************/    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return this.isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }
}
