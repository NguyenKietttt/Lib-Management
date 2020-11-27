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
    private String accountID;
    private String password;
    private String employeeID;
    private String fullName;
    private String email;
    private String phone;
    
    @Transient
    private String alert;
    
    @Transient
    private String confirmPassword;
    
    private static AccountService accountSvc = new AccountService();
    private static EmployeeService employeeSvc = new EmployeeService();
    
    public AccountBean(){
        
    }
    
    public void addAccount() throws IOException{
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

            if (accountSvc.addOrSaveAccount(a)) {
                FacesContext.getCurrentInstance()
                    .getExternalContext().redirect("login.xhtml");
            }
            else
                employeeSvc.DeleteEmployee(e);
        }
        
         this.setAlert(" - Username has been used");
    }
    
    public String getMessage() {
      return alert;
   }
    
    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
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
