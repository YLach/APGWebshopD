/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * AbstractUser is an abstract class corresponding to a persistent user of the
 * webshop.
 */
@Entity(name = "USERS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_ROLE")
@Table(name = "USERS")
public abstract class AbstractUser {

    @Id
    private String username;
    private String password;

    /**
     * Creates an instance of AbstractUser.
     */
    public AbstractUser() {
    }

    /**
     * Creates an instance of AbstractUser with the specified username and
     * password.
     *
     * @param username The user's username.
     * @param password The user's password.
     */
    public AbstractUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /*************************************************************************
     *                          GETTERS and SETTERS                          *
     *************************************************************************/
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String id) {
        this.username = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**************************************************************************
     *                          Hashcode & Equals                             *
     **************************************************************************/
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Customer)) {
            return false;
        }
        AbstractUser other = (AbstractUser) object;
        return (this.username != null && this.username.equals(other.username));
    }

    @Override
    public String toString() {
        return "Customer{" + "username=" + username
                + ", password=" + password + '}';
    }
}
