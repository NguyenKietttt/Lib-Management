/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.Account;
import com.ndtk.pojo.Employee;
import com.ndtk.service.AccountService;
import com.ndtk.service.EmployeeService;
import java.io.IOException;
import java.util.UUID;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Transient;

/**
 *
 * @author ACER
 */

@ManagedBean(name = "accountBean")
@RequestScoped
public class AccountBean {
    private static AccountService accountSvc = new AccountService();
    private static EmployeeService employeeSvc = new EmployeeService();
    
    private String accountID;
    private String password;
    private String employeeID;
    private String fullName;
    private String email;
    private String phone;

    private String alert;
    
    private String confirmPassword;
    
    private String oldPassword;
    private String newPassword;

    public AccountBean(){
        if (FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("user") != null) {
            Account acc = (Account) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("user");
            
            this.setAccountID(acc.getAccountID());
            this.setFullName(acc.getEmployee().getEmployeeName());
            this.setEmail(acc.getEmployee().getEmail());
            this.setPhone(acc.getEmployee().getPhone());
        }
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
        
         this.setAlert("Username or Password is wrong");
    }
    
    public void addAccount() {
        Employee e = new Employee();
        
        int id = getEmployeeSvc().getEmployeeID() + 1;
        e.setEmployeeID("NV" + String.valueOf(id));
        e.setEmployeeName(fullName);
        e.setEmail(email);
        e.setPhone(phone);

        if (employeeSvc.addOrSaveEmployee(e)) {
            UUID uuid = UUID.randomUUID();
            String salt = uuid.toString();

            Account a = new Account();
            a.setAccountID(this.accountID.trim());
            a.setPasswordHash(this.password.trim());
            a.setSalt(salt);
            a.setEmployee(e);

            if (accountSvc.addAccountByStore(a)) {
                this.setAlert("");
                FacesContext context = FacesContext.getCurrentInstance();
                context.getApplication().getNavigationHandler()
                    .handleNavigation(context, null, "login?faces-redirect=true");
                
                FacesContext.getCurrentInstance().responseComplete();
            }
            else
                employeeSvc.DeleteEmployee(e);
        }
        
         this.setAlert(" - Username has been used");
    }
    
    public void updateAccount() {
        Account acc = accountSvc.getAccountByID(this.accountID);
        
        if (acc != null){
            acc.getEmployee().setEmployeeName(this.fullName);
            acc.getEmployee().setEmail(this.email);
            acc.getEmployee().setPhone(this.phone);
            
            if (this.oldPassword != null || this.oldPassword.equals("") && 
                    this.newPassword != null || this.newPassword.equals("")) {
                if (accountSvc.updatePassword(this.accountID, this.oldPassword, this.newPassword) > 0) {
                    Account acc2 = accountSvc.getAccountByID(this.accountID);
                    acc2.setEmployee(acc.getEmployee());
                    
                    if (accountSvc.addOrSaveAccount(acc2)) {
                        this.setAlert("");
                        FacesContext context = FacesContext.getCurrentInstance();
                        
                        context.getExternalContext().getSessionMap().replace("user", acc2);
                        
                        context.getApplication().getNavigationHandler()
                            .handleNavigation(context, null, "profile?faces-redirect=true");

                        FacesContext.getCurrentInstance().responseComplete();
                    }
                }
                else{
                    if (accountSvc.addOrSaveAccount(acc)) {
                        this.setAlert("");
                        FacesContext context = FacesContext.getCurrentInstance();
                        
                        context.getExternalContext().getSessionMap().replace("user", acc);
                        
                        context.getApplication().getNavigationHandler()
                            .handleNavigation(context, null, "profile?faces-redirect=true");

                        FacesContext.getCurrentInstance().responseComplete();
                    }
                }
            }
        }
    }
    
    public String getMessage() {
      return alert;
   }
    
    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
    /**
     * @return the oldPassword
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * @param oldPassword the oldPassword to set
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    /**
     * @return the confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * @param confirmPassword the confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
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
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
