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

public class DashBoardRes {
    private int bookborrow;
    private int bookReturn;
    private int bookLost;
    private String fines;
    private int month;
    
    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
    /**
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(int month) {
        this.month = month;
    }
    
    /**
     * @return the bookborrow
     */
    public int getBookborrow() {
        return bookborrow;
    }

    /**
     * @param bookborrow the bookborrow to set
     */
    public void setBookborrow(int bookborrow) {
        this.bookborrow = bookborrow;
    }

    /**
     * @return the bookReturn
     */
    public int getBookReturn() {
        return bookReturn;
    }

    /**
     * @param bookReturn the bookReturn to set
     */
    public void setBookReturn(int bookReturn) {
        this.bookReturn = bookReturn;
    }

    /**
     * @return the bookLost
     */
    public int getBookLost() {
        return bookLost;
    }

    /**
     * @param bookLost the bookLost to set
     */
    public void setBookLost(int bookLost) {
        this.bookLost = bookLost;
    }

    /**
     * @return the fines
     */
    public String getFines() {
        return fines;
    }

    /**
     * @param fines the fines to set
     */
    public void setFines(String fines) {
        this.fines = fines;
    }
    // </editor-fold>
}
