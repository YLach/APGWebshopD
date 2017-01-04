/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.controller;

/**
 * InvalidCustomerException corresponds to an exception thrown when an error
 * occurs during the registration of a new customer.
 */
public class InvalidCustomerException extends Exception {

    private static final long serialVersionUID = 16247164401L;

    /**
     * Creates a new instance of <code>InvalidCustomerException</code> without
     * detail message.
     */
    public InvalidCustomerException() {
        super("Invalid customer");
    }

    /**
     * Constructs an instance of <code>InvalidCustomerException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidCustomerException(String msg) {
        super(msg);
    }
}
