/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.Card;
import com.ndtk.pojo.Reader;
import com.ndtk.service.CardService;
import com.ndtk.service.ReaderService;
import java.sql.Date;
import java.util.TimeZone;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author ACER
 */

@ManagedBean(name="readerBean")
@RequestScoped
public class ReaderBean {
    private static ReaderService readerSvc = new ReaderService();
    private static CardService cardSvc = new CardService();
    
    private String cardID;
    private Date createDate = null;
    private Date dueDate = null;
    private String readerName = "";
    private String email = "";
    private String phone = "";
    private String status = "";
    
    public ReaderBean(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
    
    public void searchReader(){
        Reader r = readerSvc.getReaderByCardID(cardID);
        
        if (r == null) {
            this.status = "CarID does not exit";
            return;
        }
        
//        java.util.Date dueDate = r.getCard().getDueDate();
//        java.util.Date currentDate = new java.util.Date();
//        if (dueDate.before(currentDate)) {
//            this.status = "Card is expired. Please update your library card";
//            return;
//        }

        Card c = cardSvc.getCardByID(r.getCard().getCardID());
        
        this.setCreateDate(r.getCard().getCreateDate());
        this.setDueDate(r.getCard().getDueDate());
        this.setReaderName(r.getReaderName());
        this.setEmail(r.getEmail());
        this.setPhone(r.getPhone());

    }
    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
     /**
     * @return the cardID
     */
    public String getCardID() {
        return cardID;
    }

    /**
     * @param cardID the cardID to set
     */
    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
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

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the cardSvc
     */
    public static CardService getCardSvc() {
        return cardSvc;
    }

    /**
     * @param aCardSvc the cardSvc to set
     */
    public static void setCardSvc(CardService aCardSvc) {
        cardSvc = aCardSvc;
    }
}