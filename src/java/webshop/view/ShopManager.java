/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.view;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import webshop.controller.InsufficientGnomeQuantityException;
import webshop.controller.InvalidGnomeQuantityException;
import webshop.controller.WebshopController;
import webshop.model.GnomeDTO;

/**
 * ShopManager is a managed bean with a SessionScoped scope and is associated
 * with the view dealing with customer's shopping basket.
 */
@Named(value = "shopManager")
@SessionScoped
public class ShopManager implements Serializable {

    private static final long serialVersionUID = 16247164401L;

    @EJB
    private WebshopController webshopController;
    @Inject
    private LoginManager loginManager;

    // Gnomes in the current user's shopping basket
    //private List<Entry<GnomeDTO, Integer>> basketEntries;
    private Map<GnomeDTO, Integer> basketEntries;
    
    
    // Quantity of each gnome to add to the shopping basket
    private Map<GnomeDTO, Integer> quantityToBuy = new LinkedHashMap<>();

    /**
     * Creates a new instance of ShopManager.
     */
    public ShopManager() {
    }

    /**
     * Initializes the ShopManager fields : shopping basket and quantity to add
     * to the basket.
     */
    @PostConstruct
    public void init() {
        Map<GnomeDTO, Integer> basket
            = webshopController.getShoppingBasket(loginManager.getUsername());
        //this.basketEntries = new ArrayList<>(basket.entrySet());
        basketEntries = basket;
    }
    
    /**
     * Initializes the quantity of each gnome to add in the user's basket.
     *
     * @param inventory The list of gnomes in the inventory.
     */
    public void initQuantityToBuy(Set<GnomeDTO> inventory) {
        quantityToBuy.clear();
        for (GnomeDTO gnome : inventory) {
            quantityToBuy.put(gnome, 0);
        }
    }

    /**
     * Adds units of the specified gnome in the shopping basket.
     *
     * @param gnome The gnome to which add units.
     * @return The next web page to be displayed.
     */
    public String addGnomeToBasket(GnomeDTO gnome) {
        try {
            webshopController.addItemToShoppingBasket(loginManager.getUsername(),
                    gnome, quantityToBuy.get(gnome));
            //Update the basket entries            
            basketEntries = webshopController.getShoppingBasket(loginManager.getUsername());
            // Message success
            String msgSuccess = quantityToBuy.get(gnome)
                    + " units of gnomes " + gnome.getType()
                    + " added to your shopping basket !";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    msgSuccess, null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            
            return Navigation.redirectToInventory();
        } catch (InvalidGnomeQuantityException | InsufficientGnomeQuantityException ex) {
            String msgError = " Invalid quantity for gnome " + gnome.getType();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msgError, null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return Navigation.toInventory();
        }
    }

    /**
     * Updates the quantity of the specified gnome in the shopping basket 
     * @param gnome The gnome to update in the shopping basket
     * @return The next web page to be displayed.
     */
    public String updateGnomeInBasket(GnomeDTO gnome) {
        try {
            webshopController.updateItemInShoppingBasket(loginManager.getUsername(),
                    gnome, basketEntries.get(gnome));
            //Update the basket entries  
            basketEntries = webshopController.getShoppingBasket(loginManager.getUsername());
                
            // Message success
            String msgSuccess = "Gnome " + gnome.getType()
                    + " updated !";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    msgSuccess, null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

            return Navigation.redirectToBasket();
        } catch (InvalidGnomeQuantityException | InsufficientGnomeQuantityException ex) {
            String msgError = " Invalid quantity for gnome " + gnome.getType();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msgError, null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return Navigation.toBasket();
        }
    }
    
    
    /**
     * Simulates the purchase of the user's shopping basket.
     *
     * @return The next web page to be displayed.
     */
    public String buyBasket() {
        webshopController.buyShoppingBasket(loginManager.getUsername());
        //Update the basket entries  
        basketEntries = webshopController.getShoppingBasket(loginManager.getUsername());
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "The shopping basket has been bought !", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return Navigation.toBasket();
    }

    /*************************************************************************
     *                          GETTERS and SETTERS                          *
     *************************************************************************/
    
    public Map<GnomeDTO, Integer> getQuantityToBuy() {
        return quantityToBuy;
    }

    public void setQuantityToBuy(Map<GnomeDTO, Integer> quantityToBuy) {
        this.quantityToBuy = quantityToBuy;
    }

    public Map<GnomeDTO, Integer> getBasketEntries() {
        return this.basketEntries;
    }

}
