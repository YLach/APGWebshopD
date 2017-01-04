/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.view;

import javax.inject.Named;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import webshop.controller.GnomeRemovalException;
import webshop.controller.InvalidGnomeQuantityException;
import webshop.controller.WebshopController;
import webshop.model.GnomeDTO;

/**
 * InventoryManager is a managed bean with a RequestScoped scoped and associated
 * with the views dealing with the gnome inventory.
 */
@Named(value = "inventoryManager")
@RequestScoped
public class InventoryManager implements Serializable {

    private static final long serialVersionUID = 16247164401L;

    @EJB
    private WebshopController webshopController;

    @Inject
    private ShopManager shopManager;

    private Set<GnomeDTO> inventory; // All the gnomes in the inventory
    private Set<GnomeDTO> inventoryForSelling; // All the gnomes in the inventory with qty > 0 

    // Quantity of gnomes to add to the inventory
    private Map<GnomeDTO, Integer> quantityToAdd = new LinkedHashMap<>();

    /**
     * Creates a new instance of InventoryManager.
     */
    public InventoryManager() {
    }

    /**
     * Initializes the InventoryManager fields : inventory and quantity to add
     * collections.
     */
    @PostConstruct
    public void init() {
        webshopController.initInventory();
        this.inventory = webshopController.getInventory();
        this.inventoryForSelling = webshopController.getInventoryForSelling();
        shopManager.initQuantityToBuy(this.inventoryForSelling);
        this.initQuantityAdd(this.inventory);
    }

    /**
     * Initializes the quantity of each gnome to add in the inventory.
     *
     * @param inventory The list of gnomes in the inventory.
     */
    public void initQuantityAdd(Set<GnomeDTO> inventory) {
        quantityToAdd.clear();
        for (GnomeDTO gnome : inventory) {
            quantityToAdd.put(gnome, 0);
        }
    }

    /**
     * Adds units of the specified gnome in the inventory.
     *
     * @param gnome The gnome to which add units.
     * @return The next web page to be displayed.
     */
    public String addUnitsToInventory(GnomeDTO gnome) {
        try {
            webshopController.addUnitsToInventory(gnome, quantityToAdd.get(gnome));
            // Message success
            String msgSuccess = quantityToAdd.get(gnome)
                    + " units of gnomes " + gnome.getType()
                    + " added to the inventory !";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    msgSuccess, null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

            return Navigation.redirectToInventory();
        } catch (InvalidGnomeQuantityException ex) {
            String msgError = " Invalid quantity for gnome " + gnome.getType();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msgError, null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return Navigation.toInventory();
        }
    }

    /**
     * Removes units of the specified gnome in the inventory.
     *
     * @param gnome The gnome to which remove units.
     * @return The next web page to be displayed.
     */
    public String removeGnomeFromInventory(GnomeDTO gnome) {
        try {
            webshopController.removeGnomeFromInventory(gnome);
            // Message success
            String msgSuccess = gnome.getType()
                    + " has been removed from inventory !";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    msgSuccess, null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

            return Navigation.redirectToInventory();
        } catch (GnomeRemovalException ex) {
            //TODO 
            return Navigation.toInventory();
        }

    }

    /**
     * Determines whether the specified gnome may be remove from the inventory,
     * that is if it is not currently in shopping baskets of some customers.
     *
     * @param gnome The gnomes to remove.
     * @return True if it can be removed.
     */
    public boolean safeToRemoveFromInventory(GnomeDTO gnome) {
        return webshopController.safeToRemoveFromInventory(gnome);
    }

    /*************************************************************************
     *                          GETTERS and SETTERS                          *
     *************************************************************************/
    
    public Set<GnomeDTO> getInventoryForSelling() {
        return inventoryForSelling;
    }

    public Set<GnomeDTO> getInventory() {
        return inventory;
    }

    public Map<GnomeDTO, Integer> getQuantityToAdd() {
        return this.quantityToAdd;
    }
}
