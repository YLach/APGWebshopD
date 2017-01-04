/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Admin is a class corresponding to a persistent administrator of the webshop.
 */
@Entity
@DiscriminatorValue(value = "Admin")
public class Admin extends AbstractUser {

    /**
     * Creates a new instance of Admin.
     */
    public Admin() {
    }

    /**
     * Creates a new instance of Admin with the specified username and password.
     *
     * @param username The admin's username.
     * @param password The admin's password.
     */
    public Admin(String username, String password) {
        super(username, password);
    }
}
