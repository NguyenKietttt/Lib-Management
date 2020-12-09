/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ACER
 */

@ManagedBean(name="borrowBean")
@SessionScoped
public class BorrowBean implements Serializable{
    private ResourceBundle bundle = ResourceBundle.getBundle("book", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    
    private String alert = "";
    
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getExternalContext().getSessionMap().get("cart") == null) {
            context.getExternalContext().getSessionMap().put("cart", new HashMap<>());
        }
    }
    
    public ArrayList<Map<String, Object>> getCarts(){
        Map<Integer, Object> cart = (Map<Integer, Object>) FacesContext.getCurrentInstance()
            .getExternalContext().getSessionMap().get("cart");
        
        ArrayList<Map<String, Object>> listBook = new ArrayList<>();
        
        for (Object o : cart.values()) {
            Map<String, Object> b = (Map<String, Object>) o;
            
            listBook.add(b);
        }
        
        return listBook;
    }
    
    public void addToCart(int bookID, String bookName){
        Map<Integer, Object> cart = (Map<Integer, Object>) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("cart");
        
        if (cart.size() >= 5){
            this.alert = this.bundle.getString("bookborrow.sizeCart");
            return;
        }
        
        int count = 0;
        for (Integer integer : cart.keySet()) {
            Map<String, Object> b = (Map<String, Object>) cart.get(integer);
            count += (int) b.get("count");
            if (count >= 5) {
                this.alert = this.bundle.getString("bookborrow.sizeCart");
                return; 
            }
        }

        if (cart.get(bookID) == null) {
            Map<String, Object> b = new HashMap<>();
            b.put("bookID", bookID);
            b.put("bookName", bookName);
            b.put("count", 1);

            cart.put(bookID, b);
            this.alert = "";
        }
        else{
            Map<String, Object> b = (Map<String, Object>) cart.get(bookID);

            int temp = (int) b.get("count") + 1;
            b.put("count", temp);
            this.alert = "";
        }
    }
    
    public void removeFromCart(int bookID){
        Map<Integer, Object> cart = (Map<Integer, Object>) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("cart");
        
        Map<String, Object> b = (Map<String, Object>) cart.get(bookID);
        
        int temp = (int) b.get("count");
        if (temp > 1) {
            temp -= 1;
            b.put("count", temp);
        }
        else
            cart.remove(bookID);
    }

    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
    /**
     * @return the bundle
     */
    public ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * @param bundle the bundle to set
     */
    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }
    
    /**
     * @return the alert
     */
    public String getAlert() {
        return alert;
    }

    /**
     * @param alert the alert to set
     */
    public void setAlert(String alert) {
        this.alert = alert;
    }
    // </editor-fold>
}
