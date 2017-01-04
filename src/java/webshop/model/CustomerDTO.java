/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.model;

/**
 * CustomerDTO is an interface defining the read-only method allowing to
 * retrieve the data of a persistent Customer.
 */
public interface CustomerDTO {

    /**
     * Returns the customer's username.
     *
     * @return The customer's username.
     */
    public String getUsername();

    /**
     * Returns the customer's password.
     *
     * @return The customer's password.
     */
    public String getPassword();

    /**
     * Checks whether the customer is banned or not.
     *
     * @return True if the customer is banned.
     */
    public boolean isBanned();
}
