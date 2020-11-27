/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

/**
 *
 * @author ACER
 */

@Entity

@NamedStoredProcedureQuery(
        name = "spAddAccount",
        procedureName = "spAddAccount",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "AccountID"),
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "Password"),
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "Salt"),
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "EmployeeID")
        }
)

@Table(name = "Account")
public class Account implements Serializable{
    @Id
    private String AccountID;
    
    @Column(name = "PasswordHash")
    private String PasswordHash;
    
    @Column(name = "Salt")
    private String Salt;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EmployeeID")
    private Employee employee;

    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
    /**
     * @param AccountID the AccountID to set
     */
    public void setAccountID(String AccountID) {
        this.AccountID = AccountID;
    }

    /**
     * @return the PasswordHash
     */
    public String getPasswordHash() {
        return PasswordHash;
    }

    /**
     * @param PasswordHash the PasswordHash to set
     */
    public void setPasswordHash(String PasswordHash) {
        this.PasswordHash = PasswordHash;
    }

    /**
     * @return the Salt
     */
    public String getSalt() {
        return Salt;
    }

    /**
     * @param Salt the Salt to set
     */
    public void setSalt(String Salt) {
        this.Salt = Salt;
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
     * @return the AccountID
     */
    public String getAccountID() {
        return AccountID;
    }
    // </editor-fold>
}
