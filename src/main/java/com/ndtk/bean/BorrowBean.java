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
            this.alert = "You already have 5 book in borrow";
            return;
        }
        
        int count = 0;
        for (Integer integer : cart.keySet()) {
            Map<String, Object> b = (Map<String, Object>) cart.get(integer);
            count += (int) b.get("count");
            if (count >= 5) {
                this.alert = "You already have 5 book in borrow";
                return; 
            }
        }

        if (cart.get(bookID) == null) {
            Map<String, Object> b = new HashMap<>();
            b.put("bookID", bookID);
            b.put("bookName", bookName);
            b.put("count", 1);

            cart.put(bookID, b);
        }
        else{
            Map<String, Object> b = (Map<String, Object>) cart.get(bookID);

            int temp = (int) b.get("count") + 1;
            b.put("count", temp);
        }
    }
    
    public void removeFromCart(int bookID){
        Map<Integer, Object> cart = (Map<Integer, Object>) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("cart");
        
        cart.remove(bookID);
    }

    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
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
