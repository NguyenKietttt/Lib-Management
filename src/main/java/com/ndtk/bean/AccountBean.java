/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.Account;
import com.ndtk.service.AccountService;
import com.ndtk.service.EmployeeService;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ACER
 */

@ManagedBean(name = "accountBean")
@RequestScoped
public class AccountBean {
    private ResourceBundle bundle = ResourceBundle.getBundle("book", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    
    private static AccountService accountSvc = new AccountService();
    private static EmployeeService employeeSvc = new EmployeeService();
    
    private String accountID;
    private String password;

    private String alert;

    public AccountBean(){

    }
    
    public void checkLogin(){
        FacesContext context = FacesContext.getCurrentInstance();
         
        if (context.getExternalContext().getSessionMap().get("user") != null) {
            context.getApplication()
                .getNavigationHandler().handleNavigation(context, null, "book?faces-redirect=true");
            
            FacesContext.getCurrentInstance().responseComplete();
        }
    }
    
    public void checkAdmin(){
        FacesContext context = FacesContext.getCurrentInstance();
        Account acc = (Account) context.getExternalContext().getSessionMap().get("user");
        if (acc != null) {
            if (!acc.getUserType().equals("Admin")) {
                context.getApplication()
                    .getNavigationHandler().handleNavigation(context, null, "book?faces-redirect=true");
            
                FacesContext.getCurrentInstance().responseComplete();
            }
        }
    }
    
    public void checkNotLogin(){
        FacesContext context = FacesContext.getCurrentInstance();
         
        if (context.getExternalContext().getSessionMap().get("user") == null) {
            context.getApplication()
                .getNavigationHandler().handleNavigation(context, null, "login?faces-redirect=true");
            
            FacesContext.getCurrentInstance().responseComplete();
        }
    }
    
    public void logoutAccount(){
        FacesContext context = FacesContext.getCurrentInstance();
        
        context.getExternalContext().getSessionMap().remove("user");
        
        context.getApplication()
            .getNavigationHandler().handleNavigation(context, null, "login?faces-redirect=true");
            
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void loginAccount() {
        if (accountSvc.loginAccount(this.accountID, this.password) > 0) {
            this.setAlert("");
            
            Account acc = accountSvc.getAccountByID(this.accountID);
            
            FacesContext context = FacesContext.getCurrentInstance();
            
            context.getExternalContext().getSessionMap().put("user", acc);
            
            context.getApplication()
                    .getNavigationHandler().handleNavigation(context, null, "book?faces-redirect=true");
            
            FacesContext.getCurrentInstance().responseComplete();
            
            return;
        }
        
         this.setAlert(this.bundle.getString("login.loginWrong"));
    }
    
    public String getMessage() {
      return alert;
   }
    
    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
    /**
     * @return the bundle
     */
    public ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * @param bundle the bundle to set
     */
    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }
    
    /**
     * @return the alert
     */
    public String getAlert() {
        return alert;
    }

    /**
     * @param alert the alert to set
     */
    public void setAlert(String alert) {
        this.alert = alert;
    }
    
    /**
     * @return the AccountID
     */
    public String getAccountID() {
        return accountID;
    }

    /**
     * @param AccountID the AccountID to set
     */
    public void setAccountID(String AccountID) {
        this.accountID = AccountID;
    }

    /**
     * @return the Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param Password the Password to set
     */
    public void setPassword(String Password) {
        this.password = Password;
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

    /**
     * @return the employeeSvc
     */
    public static EmployeeService getEmployeeSvc() {
        return employeeSvc;
    }

    /**
     * @param aEmployeeSvc the employeeSvc to set
     */
    public static void setEmployeeSvc(EmployeeService aEmployeeSvc) {
        employeeSvc = aEmployeeSvc;
    }
    // </editor-fold>
}
