/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author ACER
 */

@Entity
@Table(name = "BorrowReturnDetail")
public class BorrowReturnDetail implements Serializable{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "BorrowReturnID")
    private BorrowReturn borrowReturn;
    
    @ManyToOne
    @JoinColumn(name = "BookID")
    private Book book;
    
    @Column(name = "Returned")
    private boolean returned;
    
    @Column(name = "Lost")
    private boolean lost;
    
    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the borrowReturn
     */
    public BorrowReturn getBorrowReturn() {
        return borrowReturn;
    }

    /**
     * @param borrowReturn the borrowReturn to set
     */
    public void setBorrowReturn(BorrowReturn borrowReturn) {
        this.borrowReturn = borrowReturn;
    }

    /**
     * @return the book
     */
    public Book getBook() {
        return book;
    }

    /**
     * @param book the book to set
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * @return the returned
     */
    public boolean isReturned() {
        return returned;
    }

    /**
     * @param returned the returned to set
     */
    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    /**
     * @return the lost
     */
    public boolean isLost() {
        return lost;
    }

    /**
     * @param lost the lost to set
     */
    public void setLost(boolean lost) {
        this.lost = lost;
    }
    // </editor-fold>
}
