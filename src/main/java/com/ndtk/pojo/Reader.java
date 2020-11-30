/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.pojo;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author ACER
 */
@Entity
@Table(name = "Reader")
public class Reader implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int readerID;
    
    @Column(name = "ReaderName")
    private String readerName;
    
    @Column(name = "Email")
    private String email;
    
    @Column(name = "Phone")
    private String phone;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "CardID")
    private Card card;
    
    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
    /**
     * @return the readerID
     */
    public int getReaderID() {
        return readerID;
    }

    /**
     * @param readerID the readerID to set
     */
    public void setReaderID(int readerID) {
        this.readerID = readerID;
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

    /**
     * @return the card
     */
    public Card getCard() {
        return card;
    }

    /**
     * @param card the card to set
     */
    public void setCard(Card card) {
        this.card = card;
    }
    // </editor-fold>
}
