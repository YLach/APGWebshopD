/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.view;

import java.io.Serializable;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import webshop.controller.InvalidCustomerException;
import webshop.controller.WebshopController;
import webshop.model.CustomerDTO;

/**
 * UserManager is a managed bean with a RequestScoped scope and is associated
 * with the view dealing with user management.
 */
@Named(value = "userManager")
@RequestScoped
public class UserManager implements Serializable {

    private static final long serialVersionUID = 16247164401L;

    @EJB
    private WebshopController webshopController;

    // The list of customers registered.
    private Set<CustomerDTO> customers;

    /**
     * Creates a new instance of UsersManager.
     */
    public UserManager() {
    }

    /**
     * Initializes the list of customers.
     */
    @PostConstruct
    public void init() {
        this.customers = webshopController.getAllCustomers();
    }

    /**
     * Bans the specified customer. Once banned, the customer cannot log on
     * anymore.
     *
     * @param customer The customer to ban.
     * @return The next web page to be displayed.
     */
    public String banCustomer(CustomerDTO customer) {
        try {
            webshopController.banCustomer(customer.getUsername());
            return Navigation.redirectToUsers();
        } catch (InvalidCustomerException ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ex.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return Navigation.toUsers();
        }
    }

    /**
     * Allows a banned customer to log on again.
     *
     * @param customer The customer to allow.
     * @return The next web page to be displayed.
     */
    public String allowCustomer(CustomerDTO customer) {
        try {
            webshopController.allowCustomer(customer.getUsername());
            return Navigation.redirectToUsers();
        } catch (InvalidCustomerException ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ex.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return Navigation.toUsers();
        }
    }

    /*************************************************************************
     *                          GETTERS and SETTERS                          *
     *************************************************************************/
    
    public Set<CustomerDTO> getCustomers() {
        return customers;
    }
}
