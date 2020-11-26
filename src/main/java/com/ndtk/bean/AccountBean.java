/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.Account;
import com.ndtk.service.AccountService;
import java.util.UUID;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author ACER
 */

@ManagedBean(name = "accountBean")
@RequestScoped
public class AccountBean {
    private String AccountID;
    private String Password;
    
    private static AccountService accountSvc = new AccountService();
    
    public AccountBean(){
        
    }
    
    public String addAccount(){
        UUID uuid = UUID.randomUUID();
        String salt = uuid.toString();
        
        Account a = new Account();
        a.setAccountID(this.AccountID);
        a.setPasswordHash(this.Password);
        a.setSalt(salt);
        
        if (accountSvc.addOrSaveAccount(a) == true) {
            return "index?faces-redirect=true";
        }
        
        return "index";
    }
    
    /**
     * @return the AccountID
     */
    public String getAccountID() {
        return AccountID;
    }

    /**
     * @param AccountID the AccountID to set
     */
    public void setAccountID(String AccountID) {
        this.AccountID = AccountID;
    }

    /**
     * @return the Password
     */
    public String getPassword() {
        return Password;
    }

    /**
     * @param Password the Password to set
     */
    public void setPassword(String Password) {
        this.Password = Password;
    }

    /**
     * @return the accountSvc
     */
    public static AccountService getAccountSvc() {
        return accountSvc;
    }

    /**
     * @param aAccountSvc the accountSvc to set
     */
    public static void setAccountSvc(AccountService aAccountSvc) {
        accountSvc = aAccountSvc;
    }
}
