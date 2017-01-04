/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.controller;

/**
 * BannedUserException corresponds to an exception thrown when a banned user
 * tries to access protected resources.
 */
public class BannedUserException extends Exception {

    /**
     * Creates a new instance of <code>BannedUserException</code> without detail
     * message.
     */
    public BannedUserException() {
        super("Banned user");
    }

    /**
     * Constructs an instance of <code>BannedUserException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BannedUserException(String msg) {
        super(msg);
    }
}
