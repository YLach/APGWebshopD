/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.controller;

/**
 * GnomeRemovalException corresponds to an exception thrown when an error occurs
 * during the removal of a gnome from the inventory.
 */
public class GnomeRemovalException extends Exception {

    /**
     * Creates a new instance of <code>GnomeRemovalException</code> without
     * detail message.
     */
    public GnomeRemovalException() {
        super("Impossible to remove a gnome from the inventory");
    }

    /**
     * Constructs an instance of <code>GnomeRemovalException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public GnomeRemovalException(String msg) {
        super(msg);
    }
}
