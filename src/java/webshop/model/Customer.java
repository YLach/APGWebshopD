/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.model;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyClass;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Customer is a class corresponding to a persistent customer of the 
 * webshop.
 */
@Entity
@DiscriminatorValue(value = "Customer")
@NamedQueries({
    @NamedQuery(name = "Users.findAllCustomer",
            query = "SELECT u FROM USERS u WHERE Type(u) = Customer")
})
public class Customer extends AbstractUser implements CustomerDTO {

    @ElementCollection
    @CollectionTable(name = "SHOPPING_BASKET",
            joinColumns = @JoinColumn(name = "CUSTOMER_NAME"))
    @MapKeyClass(Gnome.class)
    @MapKeyJoinColumn(name = "GNOME", referencedColumnName = "TYPE")
    private Map<Gnome, Integer> shoppingBasket = new HashMap<>();

    private boolean isBanned = false;

    /**
     * Creates a new instance of Customer.
     */
    public Customer() {
    }

    /**
     * Creates a new instance of Customer with the specified username and
     * password.
     *
     * @param username The customer's username.
     * @param password The customer's password.
     */
    public Customer(String username, String password) {
        super(username, password);
    }

    /**
     * Add the quantity of the specified gnome in the customer's shopping
     * basket.
     *
     * @param gnome The gnome to which add units.
     * @param quantity The quantity to add.
     */
    public void addGnomeToBasket(Gnome gnome, int quantity) {
        int quantityInBasket = quantity;
        if (shoppingBasket.containsKey(gnome)) {
            quantityInBasket += shoppingBasket.get(gnome);
        }
        shoppingBasket.put(gnome, quantityInBasket);
    }

    /**
     * Clears the customer's shopping basket.
     */
    public void clearShoppingBasket() {
        shoppingBasket.clear();
    }

    /*************************************************************************
     *                          GETTERS and SETTERS                          *
     *************************************************************************/
    
    public Map<Gnome, Integer> getShoppingBasket() {
        return this.shoppingBasket;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }
}
