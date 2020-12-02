/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.pojo;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author ACER
 */

@Entity
@Table(name = "Card")
public class Card implements Serializable{
    @Id
    private String cardID;
    
    @Column(name = "CreateDate")
    private Date createDate;
    
    @Column(name = "DueDate")
    private Date dueDate;
    
    @OneToOne(mappedBy = "card", fetch = FetchType.EAGER)
    private Reader reader;
    
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private Set<BorrowReturn> listBorrowReturn;
    
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
     * @return the listReader
     */
    public Reader getReader() {
        return reader;
    }

    /**
     * @param listReader the listReader to set
     */
    public void setListReader(Reader reader) {
        this.reader = reader;
    }
    
    /**
     * @return the listBorrowReturn
     */
    public Set<BorrowReturn> getListBorrowReturn() {
        return listBorrowReturn;
    }

    /**
     * @param listBorrowReturn the listBorrowReturn to set
     */
    public void setListBorrowReturn(Set<BorrowReturn> listBorrowReturn) {
        this.listBorrowReturn = listBorrowReturn;
    }
    // </editor-fold>
}
