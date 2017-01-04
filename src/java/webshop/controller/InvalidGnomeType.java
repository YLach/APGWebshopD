/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.controller;

/**
 * InvalidGnomeType corresponds to an exception thrown when an operation
 * happened on an unknown gnome
 */
public class InvalidGnomeType extends Exception {

    /**
     * Creates a new instance of <code>InvalidGnomeType</code> without detail
     * message.
     */
    public InvalidGnomeType() {
        super("Invalid gnome type");
    }

    /**
     * Constructs an instance of <code>InvalidGnomeType</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidGnomeType(String msg) {
        super(msg);
    }
}
