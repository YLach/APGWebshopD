/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.controller;

/**
 * InvalidGnomePriceException corresponds to an exception thrown when an invalid
 * price is set during the creation of a new gnome.
 */
public class InvalidGnomePriceException extends Exception {

    /**
     * Creates a new instance of <code>InvalidGnomePriceException</code> without
     * detail message.
     */
    public InvalidGnomePriceException() {
        super("Invalid price");
    }

    /**
     * Constructs an instance of <code>InvalidGnomePriceException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidGnomePriceException(String msg) {
        super(msg);
    }
}
