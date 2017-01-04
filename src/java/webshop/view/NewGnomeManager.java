/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.view;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import webshop.controller.InvalidGnomePriceException;
import webshop.controller.InvalidGnomeQuantityException;
import webshop.controller.InvalidGnomeType;
import webshop.controller.WebshopController;

/**
 * NewGnomeManager is a managed bean with a RequestScoped scope and is associated
 * with the view dealing with gnome creation.
 */
@Named(value = "newGnomeManager")
@RequestScoped
public class NewGnomeManager implements Serializable {

    private static final long serialVersionUID = 16247164401L;

    @EJB
    WebshopController webshopController;

    private String type;
    private Float price;
    private Integer quantity;
    private String imageURL;

    /**
     * Creates a new instance of NewGnomeManager.
     */
    public NewGnomeManager() {
    }

    /**
     * Adds a new gnome article in the inventory.
     *
     * @return The next web page to be displayed.
     */
    public String addNewGnomeToInventory() {
        boolean error = true;
        try {
            webshopController.createGnome(type, price, quantity, imageURL);
            error = false;
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
            "New gnome " + type + " added successfully !", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        } catch (InvalidGnomeType ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ex.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage("type", msg);
        } catch (InvalidGnomePriceException ex1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ex1.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage("price", msg);
        } catch (InvalidGnomeQuantityException ex2) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ex2.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage("quantity", msg);
        } finally {
            return error ? Navigation.toNewGnomeAdmin() : Navigation.toInventory();
        }
    }

    /**
     * ************************************************************************
     * GETTERS and SETTERS *
     *************************************************************************
     */
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
