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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

/**
 *
 * @author ACER
 */

@Entity
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
        name = "spAddAccount",
        procedureName = "spAddAccount",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "AccountID"),
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "Password"),
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "Salt"),
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "EmployeeID")
        }
    ),
    
    @NamedStoredProcedureQuery(
        name = "spLogin",
        procedureName = "spLogin",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "AccountID"),
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "Password"),
            @StoredProcedureParameter(mode = ParameterMode.OUT, type = Integer.class, name = "OutValue")
        }
    ),
    
    @NamedStoredProcedureQuery(
        name = "spUpdatePassword",
        procedureName = "spUpdatePassword",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "AccountID"),
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "OldPassword"),
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "NewPassword"),
            @StoredProcedureParameter(mode = ParameterMode.OUT, type = Integer.class, name = "OutValue")
        }
    )
})

@Table(name = "Account")
public class Account implements Serializable{
    @Id
    private String AccountID;
    
    @Column(name = "PasswordHash")
    private String PasswordHash;
    
    @Column(name = "Salt")
    private String Salt;
    
    @Column(name = "UserType")
    private String userType;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "EmployeeID")
    private Employee employee;

    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
    /**
     * @return the userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }
    
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
