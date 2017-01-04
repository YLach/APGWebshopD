/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.controller;

/**
 * InsufficientGnomeQuantityException corresponds to an exception thrown when a
 * customer tries to add to many gnomes into his shopping basket.
 */
public class InsufficientGnomeQuantityException extends Exception {

    /**
     * Constructs an instance of <code>InsufficientGnomeQuantityException</code>
     * with the specified gnome's name.
     *
     * @param gnomeType The name of the gnome.
     * @param quantityRemaining The quantity remaining in the inventory.
     */
    public InsufficientGnomeQuantityException(String gnomeType,
            int quantityRemaining) {
        super("Insufficient quantity of gnome " + gnomeType
                + " : only " + quantityRemaining + " remaining in the inventory");
    }
}
