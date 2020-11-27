/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ACER
 */

@Entity
@Table(name = "BorrowReturn")
public class BorrowReturn implements Serializable{
    @Id
    @Column(name = "BorrowReturnID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int borrowReturnID;
    
    @ManyToOne
    @JoinColumn(name = "CardID")
    private Card card;
    
    @ManyToOne
    @JoinColumn(name = "EmployeeID")
    private Employee employee;
    
    @Column(name = "BorrowDate")
    private Date borrowDate;
    
    @Column(name = "ReturnDate")
    private Date returnDate;
    
    @Column(name = "Fines")
    private BigDecimal fines;
    
    @OneToMany(mappedBy = "borrowReturn", cascade = CascadeType.ALL)
    private Set<BorrowReturnDetail> listBorrowReturnDetail;
    
    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
    /**
     * @return the borrowReturnID
     */
    public int getBorrowReturnID() {
        return borrowReturnID;
    }

    /**
     * @param borrowReturnID the borrowReturnID to set
     */
    public void setBorrowReturnID(int borrowReturnID) {
        this.borrowReturnID = borrowReturnID;
    }

    /**
     * @return the borrowDate
     */
    public Date getBorrowDate() {
        return borrowDate;
    }

    /**
     * @param borrowDate the borrowDate to set
     */
    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    /**
     * @return the returnDate
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * @param returnDate the returnDate to set
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * @return the fines
     */
    public BigDecimal getFines() {
        return fines;
    }

    /**
     * @param fines the fines to set
     */
    public void setFines(BigDecimal fines) {
        this.fines = fines;
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

    /**
     * @return the employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * @param employee the employee to set
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    /**
     * @return the listBorrowReturnDetail
     */
    public Set<BorrowReturnDetail> getListBorrowReturnDetail() {
        return listBorrowReturnDetail;
    }

    /**
     * @param listBorrowReturnDetail the listBorrowReturnDetail to set
     */
    public void setListBorrowReturnDetail(Set<BorrowReturnDetail> listBorrowReturnDetail) {
        this.listBorrowReturnDetail = listBorrowReturnDetail;
    }
    // </editor-fold>
}
