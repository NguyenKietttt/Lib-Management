/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.res;

/**
 *
 * @author ACER
 */

public class BookReturnRes {
    private int id;
    private int borrowReturnDetailID;
    private String bookName;
    
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
     * @return the bookName
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * @param bookName the bookName to set
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /**
     * @return the borrowReturnDetailID
     */
    public int getBorrowReturnDetailID() {
        return borrowReturnDetailID;
    }

    /**
     * @param borrowReturnDetailID the borrowReturnDetailID to set
     */
    public void setBorrowReturnDetailID(int borrowReturnDetailID) {
        this.borrowReturnDetailID = borrowReturnDetailID;
    }
    // </editor-fold>
}
