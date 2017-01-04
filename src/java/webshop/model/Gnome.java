/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * GNome is a class corresponding to a persistent gnome article of the 
 * webshop's inventory.
 */
@Entity(name = "Inventory")
@NamedQueries({
    @NamedQuery(name = "Inventory.GnomesForSelling",
            query = "SELECT i FROM Inventory i WHERE i.quantity > 0")
    ,
    @NamedQuery(name = "Inventory.GnomesInventory",
            query = "SELECT i FROM Inventory i"),})
public class Gnome implements Serializable, GnomeDTO {

    private static final long serialVersionUID = 16247164401L;

    @Id
    private String type;
    private float price;
    private int quantity;
    private String image;
    private int inBasket;

    /**
     * Creates a new instance of Gnome.
     */
    public Gnome() {
    }
    
    /**
     * Creates a new instance of Gnome with the specified type, price, quantity
     * and image url.
     * @param type The gnome's type
     * @param price The gnome's price
     * @param quantity The gnome's quantity
     * @param image The URL of the image representing the gnome if any, null 
     * otherwise
     */
    public Gnome(String type, float price, int quantity, String image) {
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.inBasket = 0;
    }

    /*************************************************************************
     *                          GETTERS and SETTERS                          *
     *************************************************************************/
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void decreaseQuantityInInventory(int quantityToDecrease) {
        setQuantity(this.quantity - quantityToDecrease);
    }

    public void increaseQuantityInInventory(int quantityToIncrease) {
        setQuantity(this.quantity + quantityToIncrease);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getInBasket() {
        return inBasket;
    }

    public void setInBasket(int inBasket) {
        this.inBasket = inBasket;
    }

    public void decreaseQuantityInBaskets(int quantityToDecrease) {
        setInBasket(this.inBasket - quantityToDecrease);
    }

    public void increaseQuantityInBaskets(int quantityToIncrease) {
        setInBasket(this.inBasket + quantityToIncrease);
    }

    /**************************************************************************
     *                          Hashcode & Equals                             *
     **************************************************************************/
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (type != null ? type.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Gnome)) {
            return false;
        }
        Gnome other = (Gnome) object;
        return (this.type != null && this.type.equals(other.type));
    }

    @Override
    public String toString() {
        return "webshop.model.Gnome[ type=" + type + " ]";
    }

}
