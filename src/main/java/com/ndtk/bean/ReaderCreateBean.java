/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.Card;
import com.ndtk.pojo.Reader;
import com.ndtk.service.CardService;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ACER
 */

@ManagedBean(name = "readerCreateBean")
@RequestScoped
public class ReaderCreateBean {
    private ResourceBundle bundle = ResourceBundle.getBundle("book", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    
    private static CardService cardSvc = new CardService();
    
    private String readerName = "";
    private String email = "";
    private String phone = "";
    
    private String alert = "";
    
    public ReaderCreateBean(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
    
    public void addReader(){
        Card c = new Card();
        Reader r = new Reader();
        
        // Reader Name
        if (!this.getReaderName().trim().equals("")) {
            r.setReaderName(this.getReaderName());
        }
        else{
           this.setAlert(this.bundle.getString("employeedetail.fullName") + " " +
                    this.bundle.getString("bookdetail.validateBlank"));
           return;
        }
        
        // Email
        if (!this.getEmail().trim().equals("")) {
            r.setEmail(this.getEmail());
        }
        else{
           this.setAlert(this.bundle.getString("bookborrow.email") + " " +
                    this.bundle.getString("bookdetail.validateBlank"));
           return;
        }
        
        // Phone
        if (!this.getPhone().trim().equals("")) {
            r.setPhone(this.getPhone());
        }
        else{
           this.setAlert(this.bundle.getString("bookborrow.phone") + " " +
                    this.bundle.getString("bookdetail.validateBlank"));
           return;
        }
        
        // Card ID
        int id = cardSvc.getCardID() + 1;
        c.setCardID("C" + String.valueOf(id));
        
        // Create Date
        LocalDate createDate = LocalDate.now();
        c.setCreateDate(java.sql.Date.valueOf(createDate));
        
        // Due Date
        LocalDate dueDate = createDate.plusMonths(6);
        c.setDueDate(java.sql.Date.valueOf(dueDate));
        
        c.setListReader(r);
        r.setCard(c);
        
        if (cardSvc.addOrSaveCard(c)) {
            FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
                .handleNavigation(FacesContext
                        .getCurrentInstance(), null, "reader-detail?faces-redirect=true&cardID=" + c.getCardID());
        }
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
    
    /**
     * @return the readerSvc
     */
    public static CardService getCardSvc() {
        return cardSvc;
    }

    /**
     * @param aReaderSvc the readerSvc to set
     */
    public static void setCardSvc(CardService cardSvc) {
        cardSvc = cardSvc;
    }

    /**
     * @return the readerName
     */
    public String getReaderName() {
        return readerName;
    }

    /**
     * @param readerName the readerName to set
     */
    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    // </editor-fold>
}
