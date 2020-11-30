/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.pojo;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author ACER
 */

@Entity
@Table(name = "Employee")
public class Employee implements Serializable{
    @Id
    private String employeeID;
    
    @Column(name = "EmployeeName")
    private String employeeName;
    
    @Column(name = "Email")
    private String email;
    
    @Column(name = "Phone")
    private String phone;
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<BorrowReturn> listBorrowReturn;
    
    @OneToOne(mappedBy = "employee")
    private Account account;
    
    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
    /**
     * @return the employeeID
     */
    public String getEmployeeID() {
        return employeeID;
    }

    /**
     * @param employeeID the employeeID to set
     */
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    /**
     * @return the employeeName
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * @param employeeName the employeeName to set
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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
    
    /**
     * @return the listAccount
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @param listAccount the listAccount to set
     */
    public void setAccount(Account listAccount) {
        this.account = listAccount;
    }
    // </editor-fold>
}