/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.view;

import java.io.Serializable;

/**
 * Navigation class provides methods used for dynamic navigation between the
 * different views of the application.
 */
public class Navigation implements Serializable {

    private static final long serialVersionUID = 16247164401L;
    private static final String ROOT_URL = "faces/";
    public static final String ADMIN_URL = ROOT_URL + "admin/";

    /**
     * Redirect to login page.
     *
     * @return Login page name.
     */
    public static String redirectToLogin() {
        return ROOT_URL + "login.xhtml?faces-redirect=true";
    }

    /**
     * Go to login page.
     *
     * @return Login page name.
     */
    public static String toLogin() {
        return ROOT_URL + "login.xhtml";
    }

    /**
     * Redirect to home page.
     *
     * @return Home page name.
     */
    public static String redirectToHome() {
        return ROOT_URL + "home.xhtml?faces-redirect=true";
    }

    /**
     * Go to home page.
     *
     * @return Home page name.
     */
    public static String toHome() {
        return "home.xhtml";
    }

    /**
     * Redirect to inventory page.
     *
     * @return Inventory page name.
     */
    public static String redirectToInventory() {
        return "inventory.xhtml?faces-redirect=true";
    }

    /**
     * Go to inventory page.
     *
     * @return Inventory page name.
     */
    public static String toInventory() {
        return "inventory.xhtml";
    }

    /**
     * Redirect to basket page.
     *
     * @return Basket page name.
     */
    public static String redirectToBasket() {
        return "basket.xhtml?faces-redirect=true";
    }

    /**
     * Go to basket page.
     *
     * @return Basket page name.
     */
    public static String toBasket() {
        return "basket.xhtml";
    }

    /**************************************************************************
     *                              ADMIN PAGES                               *
     **************************************************************************/
    
    /**
     * Redirect to admin home page.
     *
     * @return Admin home page name.
     */
    public static String redirectToHomeAdmin() {
        return ADMIN_URL + "home.xhtml?faces-redirect=true";
    }

    /**
     * Redirect to inventory page.
     *
     * @return Inventory page name.
     */
    public static String redirectToInventoryAdmin() {
        return ADMIN_URL + "inventory.xhtml?faces-redirect=true";
    }

    /**
     * Go to users page.
     *
     * @return Users page name.
     */
    public static String toUsers() {
        return "users.xhtml";
    }

    /**
     * Redirect to users page.
     *
     * @return Users page name.
     */
    public static String redirectToUsers() {
        return "users.xhtml?faces-redirect=true";
    }

    /**
     * Go to newgnome page.
     *
     * @return Newgnome page name.
     */
    public static String toNewGnomeAdmin() {
        return "newgnome.xhtml";
    }

    /**
     * Redirect to newgnome page.
     *
     * @return Newgnome page name.
     */
    public static String redirectToNewGnomeAdmin() {
        return ADMIN_URL + "newgnome.xhtml?faces-redirect=true";
    }

}
