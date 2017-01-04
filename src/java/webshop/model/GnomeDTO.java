/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.model;

/**
 * GnomeDTO is an interface defining the read-only method allowing to retrieve
 * the data of a persistent Gnome.
 */
public interface GnomeDTO {

    /**
     * Returns the type of a gnome.
     *
     * @return The type of the gnome.
     */
    public String getType();

    /**
     * Returns the price of a gnome.
     *
     * @return The price of the gnome.
     */
    public float getPrice();

    /**
     * Returns the quantity of a gnome.
     *
     * @return The quantity of the gnome.
     */
    public int getQuantity();

    /**
     * Returns the image URL of a gnome.
     *
     * @return The image URL representation of the gnome.
     */
    public String getImage();

    /**
     * Checks is the gnome is in a customer's basket.
     *
     * @return True if the gnome is currently in one shopping basket.
     */
    public int getInBasket();
}
