/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.controller;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import webshop.model.AbstractUser;
import webshop.model.Admin;
import webshop.model.Customer;
import webshop.model.CustomerDTO;
import webshop.model.Gnome;
import webshop.model.GnomeDTO;

/**
 * WebshopController is a stateful session bean implementing the business logic
 * of the webshop.
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class WebshopController {

    @PersistenceContext(unitName = "APGWebShopPU")
    private EntityManager em;

    /**************************************************************************
     *                              Customers                                 *
     **************************************************************************/
    
    /**
     * Add a new customer persistently.
     *
     * @param username The customer's username
     * @param password The customer's password
     *
     * @throws InvalidCustomerException .
     */
    public void addCustomer(String username, String password)
            throws InvalidCustomerException {
        // TODO : check if not still a admin username

        AbstractUser user = em.find(AbstractUser.class, username);
        if (user != null) {
            throw new InvalidCustomerException("User already existing");
        }
        Customer customer = new Customer(username, password);
        em.persist(customer);
    }

    /**
     * Check if the username and password corresponds to a registered customer.
     *
     * @param username The customer's username
     * @param password The customer's password
     * @return true if valid, false otherwise
     * @throws BannedUserException .
     */
    public boolean validateCustomerLogin(String username, String password)
            throws BannedUserException {
        Customer customer = em.find(Customer.class, username);
        if (customer != null && customer.isBanned()) {
            throw new BannedUserException("User " + username + " is banned");
        }
        return ((customer != null)
                && (customer.getUsername().equals(username))
                && (customer.getPassword().equals(password)));
    }

    /**************************************************************************
     *                              Admin                                     *
     **************************************************************************/    
    
    /**
     * Check if the username and password corresponds to a registered admin.
     *
     * @param username The admin's username
     * @param password The admin's password
     * @return true if valid, false otherwise
     */
    public boolean validateAdminLogin(String username, String password) {
        Admin admin = em.find(Admin.class, username);
        return ((admin != null)
                && (admin.getUsername().equals(username))
                && (admin.getPassword().equals(password)));
    }

    /**************************************************************************
     *                              Gnomes                                    *
     **************************************************************************/
    
    private enum Gnomes {
        GNOME1("GameOfGnomes", 14.69f),
        GNOME2("Magnus", 7.96f),
        GNOME3("Austin", 14.36f),
        GNOME4("JimmyTheMushroom", 8.95f),
        GNOME5("Juniper", 23.96f);

        private String type;
        private float price;

        Gnomes(String type, float price) {
            this.type = type;
            this.price = price;
        }

        String getType() {
            return type;
        }

        float getPrice() {
            return price;
        }
    }

    /**
     * Initializes the gnomes inventory by querying the database.
     */
    public void initInventory() {
        if (this.getInventory().isEmpty()) {
            EnumSet<Gnomes> enumSet = EnumSet.allOf(Gnomes.class);
            for (Gnomes current : enumSet) {
                try {
                    createGnome(current.getType(), current.getPrice(), 10, null);
                } catch (InvalidGnomePriceException
                        | InvalidGnomeQuantityException 
                        | InvalidGnomeType ex) {
                    Logger.getLogger(WebshopController.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Creates a new gnome persistently.
     *
     * @param type The gnome's type
     * @param price The gnome's price
     * @param quantity The gnome's quantity
     * @param image URL of the image representing the gnome
     * @throws InvalidGnomePriceException .
     * @throws InvalidGnomeQuantityException .
     * @throws InvalidGnomeType .
     */
    public void createGnome(String type, float price, int quantity, String image)
            throws InvalidGnomePriceException,
            InvalidGnomeQuantityException,
            InvalidGnomeType {
        if (type == null) {
            throw new InvalidGnomeType();
        }
        Gnome gnome = em.find(Gnome.class, type);
        if (gnome != null) {
            throw new InvalidGnomeType("Gnome " + type + " already in the inventory");
        }
        if (price < 0) {
            throw new InvalidGnomePriceException();
        }
        if (quantity < 0f) {
            throw new InvalidGnomeQuantityException();
        }

        Gnome newGnome = new Gnome(type, price, quantity, image);
        em.persist(newGnome);
    }

    /**
     * Returns the whole inventory.
     *
     * @return The gnomes inventory.
     */
    public Set<GnomeDTO> getInventory() {
        TypedQuery<GnomeDTO> query
                = em.createNamedQuery("Inventory.GnomesInventory", GnomeDTO.class);
        List<GnomeDTO> allGnomes = query.getResultList();
        return new HashSet<>(allGnomes);
    }

    /**
     * Returns the list of gnomes with a quantity in inventory superior to 0.
     *
     * @return The gnomes inventory.
     */
    public Set<GnomeDTO> getInventoryForSelling() {
        TypedQuery<GnomeDTO> query
                = em.createNamedQuery("Inventory.GnomesForSelling", GnomeDTO.class);
        List<GnomeDTO> allGnomes = query.getResultList();
        return new HashSet<>(allGnomes);
    }

    /**
     * Adds new units for the specified gnome in the inventory.
     *
     * @param gnome The gnome to update
     * @param unitsToAdd The number of units to add
     * @throws InvalidGnomeQuantityException .
     */
    public void addUnitsToInventory(GnomeDTO gnome, int unitsToAdd)
            throws InvalidGnomeQuantityException {
        // Valid quantity ?
        if (unitsToAdd <= 0) {
            throw new InvalidGnomeQuantityException();
        }

        Gnome gnomeToUpdate = em.find(Gnome.class, gnome.getType());
        if (gnomeToUpdate != null) {
            try {
                gnomeToUpdate.increaseQuantityInInventory(unitsToAdd);
                em.merge(gnomeToUpdate);
            } catch (Exception ex) {
                return;
            }
        }
    }

    /**
     * Removes the specified gnome from the inventory.
     *
     * @param gnomeToRemove The gnome to remove
     * @throws GnomeRemovalException .
     */
    public void removeGnomeFromInventory(GnomeDTO gnomeToRemove)
            throws GnomeRemovalException {
        Gnome gnomeToUpdate = em.find(Gnome.class, gnomeToRemove.getType());
        if (gnomeToUpdate != null) {
            //Safe to delete
            try {
                em.remove(gnomeToUpdate);
                em.flush();
            } catch (Exception ex) {
                throw new GnomeRemovalException();
            }
        }
    }

    /**
     * Determines whether or not a gnome may be remove from the inventory.
     *
     * @param gnomeToRemove The gnome to remove
     * @return true if safe to remove, false otherwise
     */
    public boolean safeToRemoveFromInventory(GnomeDTO gnomeToRemove) {
        Gnome gnome = em.find(Gnome.class, gnomeToRemove.getType());
        if (gnome != null) {
            return gnome.getInBasket() == 0;
        }
        return false;
    }

    /**************************************************************************
     *                              Shopping basket                           *
     **************************************************************************/
    
    /**
     * Add or update a gnome the number of units of the specified gnome in the
     * shopping basket of the specified user.
     *
     * @param buyerUsername The name of the customer
     * @param gnome The gnome to add
     * @param quantityToAdd The quantity of units to add in the basket
     * @throws InvalidGnomeQuantityException .
     * @throws InsufficientGnomeQuantityException .
     */
    public void addItemToShoppingBasket(String buyerUsername,
            GnomeDTO gnome, int quantityToAdd)
            throws InvalidGnomeQuantityException,
            InsufficientGnomeQuantityException {
        // Valid quantity ?
        if (quantityToAdd <= 0) {
            throw new InvalidGnomeQuantityException();
        }

        Customer customer = em.find(Customer.class, buyerUsername);
        if (customer != null) {
            //Remove from inventory
            Gnome gnomeToAdd = em.find(Gnome.class, gnome.getType());
            if (gnomeToAdd != null) {
                //Check if enough items remaining in the inventory
                if (gnomeToAdd.getQuantity() < quantityToAdd) {
                    //Not okay
                    throw new InsufficientGnomeQuantityException(gnome.getType(),
                            gnome.getQuantity());
                }
                // Okay
                try {
                    gnomeToAdd.decreaseQuantityInInventory(quantityToAdd);
                    gnomeToAdd.increaseQuantityInBaskets(quantityToAdd);
                    em.merge(gnomeToAdd);
                } catch (Exception ex) {
                    return;
                }

                customer.addGnomeToBasket(gnomeToAdd, quantityToAdd);
                em.merge(customer);
            }
        }
    }
    
    public void updateItemInShoppingBasket(String buyerUsername,GnomeDTO gnome,
            int newQuantity) throws InvalidGnomeQuantityException,
            InsufficientGnomeQuantityException {
        // Valid quantity ?
        if (newQuantity < 0) {
            throw new InvalidGnomeQuantityException();
        }
        
        Customer customer = em.find(Customer.class, buyerUsername);
        if (customer != null) {
            //Remove from inventory
            Gnome gnomeToAdd = em.find(Gnome.class, gnome.getType());
            if (gnomeToAdd != null) {
                int oldQuantity = customer.getShoppingBasket().get(gnome);
                int diffToUpdate = newQuantity - oldQuantity;
                
                if (diffToUpdate >= 0) {
                    //Check if enough items remaining in the inventory
                    if (gnomeToAdd.getQuantity() < diffToUpdate) {
                        //Not okay
                        throw new InsufficientGnomeQuantityException(gnome.getType(),
                                gnome.getQuantity());
                    }
                    
                    //Add new units
                    try {
                        gnomeToAdd.decreaseQuantityInInventory(diffToUpdate);
                        gnomeToAdd.increaseQuantityInBaskets(diffToUpdate);
                        em.merge(gnomeToAdd);
                        
                        customer.addGnomeToBasket(gnomeToAdd, diffToUpdate);
                        em.merge(customer);
                    } catch (Exception ex) {
                        return;
                    }
                } else {
                    diffToUpdate = -diffToUpdate;
                    //Remove units from basket
                    try {
                        gnomeToAdd.increaseQuantityInInventory(diffToUpdate);
                        gnomeToAdd.decreaseQuantityInBaskets(diffToUpdate);
                        em.merge(gnomeToAdd);
                        
                        customer.removeGnomeToBasket(gnomeToAdd, diffToUpdate);
                        em.merge(customer);
                    } catch (Exception ex) {
                        return;
                    }
                }
            }
        }
    }

    /**
     * Returns the shopping basket of the specified user.
     *
     * @param buyerUsername The name of the customer
     * @return The Map of gnomes in the customer's shopping basket and for each,
     * the quantity of units.
     */
    public Map<GnomeDTO, Integer> getShoppingBasket(String buyerUsername) {
        Map<GnomeDTO, Integer> customerBasket = null;
        Customer customer = em.find(Customer.class, buyerUsername);
        if (customer != null) {
            customerBasket = new HashMap<>(customer.getShoppingBasket());
        }
        return customerBasket;
    }

    /**
     * Simulates the buying of the specified user's shopping basket. It simply
     * empties the shopping basket.
     *
     * @param buyerUsername THe customer name
     */
    public void buyShoppingBasket(String buyerUsername) {
        Customer customer = em.find(Customer.class, buyerUsername);
        if (customer != null) {
            for (Map.Entry<Gnome, Integer> entry : customer.getShoppingBasket().entrySet()) {
                Gnome tmp = em.find(Gnome.class, entry.getKey().getType());
                if (tmp != null) {
                    tmp.decreaseQuantityInBaskets(entry.getValue());
                    em.merge(tmp);
                }
            }
            customer.clearShoppingBasket();
            em.merge(customer);
        }
    }


    /**************************************************************************
     *                              Users                                     *
     **************************************************************************/    
    
    /**
     * Return the list of all user that are customers of the webshop. In other
     * word, administrators are not part of this list.
     *
     * @return The list of customers
     */
    public Set<CustomerDTO> getAllCustomers() {
        TypedQuery<CustomerDTO> query
                = em.createNamedQuery("Users.findAllCustomer", CustomerDTO.class);
        List<CustomerDTO> allCustomers = query.getResultList();
        return new HashSet<>(allCustomers);
    }

    /**
     * Bans the specified user. Once banned, the user is still persistently
     * stored but he cannot log in.
     *
     * @param username The customer's username to ban
     * @throws InvalidCustomerException .
     */
    public void banCustomer(String username) throws InvalidCustomerException {
        try {
            Customer customer = em.find(Customer.class, username);
            customer.setIsBanned(true);
            buyShoppingBasket(username);
            em.merge(customer);
        } catch (Exception ex) {
            throw new InvalidCustomerException("Invalid customer " + username);
        }
    }

    /**
     * Allows the specified banned user. If the user is not banned, nothing
     * happen.
     *
     * @param username The customer's username to allow
     * @throws InvalidCustomerException .
     */
    public void allowCustomer(String username) throws InvalidCustomerException {
        try {
            Customer customer = em.find(Customer.class, username);
            if (customer != null && customer.isBanned()) {
                customer.setIsBanned(false);
                em.merge(customer);
            }
        } catch (Exception ex) {
            throw new InvalidCustomerException("Invalid customer " + username);
        }
    }

    /**
     * Checks if the specified user is banned.
     *
     * @param username The user's username
     * @return true is banned, false otherwise
     */
    public boolean isBanned(String username) {
        Customer customer = em.find(Customer.class, username);
        if (customer != null) {
            return customer.isBanned();
        }
        return false;
    }
}
