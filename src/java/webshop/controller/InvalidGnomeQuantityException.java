/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.controller;

/**
 * InvalidGnomeQuantityException corresponds to an exception thrown when an
 * invalid quantity is set during the creation/buying of a gnome.
 */
public class InvalidGnomeQuantityException extends Exception {

    /**
     * Constructs an instance of <code>InvalidGnomeQuantity</code> with the
     * specified detail message.
     *
     */
    public InvalidGnomeQuantityException() {
        super("Invalid quantity");
    }
}
