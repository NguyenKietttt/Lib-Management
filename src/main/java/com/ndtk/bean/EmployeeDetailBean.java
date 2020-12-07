/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.Account;
import com.ndtk.pojo.BorrowReturn;
import com.ndtk.pojo.Employee;
import com.ndtk.service.AccountService;
import com.ndtk.service.EmployeeService;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ACER
 */

@ManagedBean(name = "employeeDetailBean")
@RequestScoped
public class EmployeeDetailBean {
    private static AccountService accountSvc = new AccountService();
    private static EmployeeService employeeSvc = new EmployeeService();
    
    private static Employee emp;
    
    private static Set<BorrowReturn> listBR;
    
    private String accountID;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    
    private String confirmPassword;
    private String alert;
    private String oldPassword;
    private String newPassword;
    
    public EmployeeDetailBean(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        FacesContext context = FacesContext.getCurrentInstance();
        
        emp = new Employee();
        
        if (!context.isPostback()) {
            accountID = null;
            password = null;
            fullName = null;
            email = null;
            phone = null;
            listBR = null;
            
            Account acc = (Account) context.getExternalContext().getSessionMap().get("user");
            
            if (acc.getUserType().equals("Employee"))
                emp = acc.getEmployee();
            else{
                String id = context.getExternalContext().getRequestParameterMap().get("employeeID");
                
                if (id != null && !id.isEmpty()){
                    Pattern pattern = Pattern.compile("^[NV.2-9]*$");
                    Matcher matcher = pattern.matcher(id);

                    if (matcher.matches())
                        emp = employeeSvc.getEmployeeByID(id);
                    else
                        FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
                                .handleNavigation(FacesContext.getCurrentInstance(), null, "employee?faces-redirect=true");
                }
            }
            
            if (emp != null) {
                this.fullName = emp.getEmployeeName();
                this.email = emp.getEmail();
                this.phone = emp.getPhone();
                this.listBR = emp.getListBorrowReturn();
            }
        }
    }
    
    public void addAccount() {
        Employee e = new Employee();
        
        // Employee ID
        int id = employeeSvc.getEmployeeID() + 1;
        e.setEmployeeID("NV" + String.valueOf(id));
        
        // Employee Name
        if (!this.fullName.trim().equals("")) {
            e.setEmployeeName(this.fullName);
        }
        else{
           this.alert = "Employee Name cannot be blank";
           return;
        }
        
        // Email
        if (!this.email.trim().equals("")) {
            e.setEmail(this.email);
        }
        else{
           this.alert = "Email cannot be blank";
           return;
        }
        
        // Phone
        if (!this.phone.trim().equals("")) {
            e.setPhone(this.phone);
        }
        else{
           this.alert = "Phone cannot be blank";
           return;
        }
        
        // Username
        if (this.accountID.trim().equals("")) {
            this.alert = "Username cannot be blank";
           return;
        }
        
        // Password
        if (this.password.trim().equals("")) {
            this.alert = "Username cannot be blank";
        }

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
                    .handleNavigation(context, null, "employee?faces-redirect=true");
                
                FacesContext.getCurrentInstance().responseComplete();
            }
            else
                employeeSvc.DeleteEmployee(e);
        }
        
         this.setAlert("Username has been used");
    }
    
    public void updateAccount() {
        // Employee Name
        if (!this.fullName.trim().equals("")) {
            emp.setEmployeeName(this.fullName);
        }
        else{
           this.alert = "Employee Name cannot be blank";
           return;
        }
        
        // Email
        if (!this.email.trim().equals("")) {
            emp.setEmail(this.email);
        }
        else{
           this.alert = "Email cannot be blank";
           return;
        }
        
        // Phone
        if (!this.phone.trim().equals("")) {
            emp.setPhone(this.phone);
        }
        else{
           this.alert = "Phone cannot be blank";
           return;
        }

        // Password
        if (!this.oldPassword.equals("") && !this.newPassword.equals("")) {
            if (accountSvc.updatePassword(emp.getAccount().getAccountID(), this.oldPassword, this.newPassword) > 0) {
                Account acc2 = accountSvc.getAccountByID(emp.getAccount().getAccountID());
                acc2.setEmployee(emp);

                if (accountSvc.addOrSaveAccount(acc2)) {
                    this.setAlert("");
                    FacesContext context = FacesContext.getCurrentInstance();
                    Account acc = (Account) context.getExternalContext().getSessionMap().get("user");
                    
                    if (acc.getUserType().equals("Employee")) {
                        context.getExternalContext().getSessionMap().replace("user", acc2);
                        
                        context.getApplication().getNavigationHandler()
                            .handleNavigation(context, null, "employee-detail?faces-redirect=true");

                        FacesContext.getCurrentInstance().responseComplete();
                    }
                    else
                        context.getApplication().getNavigationHandler()
                            .handleNavigation(context, null, "employee-detail?faces-redirect=true&employeeID=" + emp.getEmployeeID());
                }
            }
            else
                this.alert = "Not equal";
        }
        else{
            if (accountSvc.addOrSaveAccount(emp.getAccount())) {
                this.setAlert("");
                FacesContext context = FacesContext.getCurrentInstance();
                Account acc = (Account) context.getExternalContext().getSessionMap().get("user");

                if (acc.getUserType().equals("Employee")) {
                    context.getExternalContext().getSessionMap().replace("user", emp.getAccount());

                    context.getApplication().getNavigationHandler()
                        .handleNavigation(context, null, "employee-detail?faces-redirect=true");

                    FacesContext.getCurrentInstance().responseComplete();
                }
                else
                    context.getApplication().getNavigationHandler()
                        .handleNavigation(context, null, "employee-detail?faces-redirect=true&employeeID=" + emp.getEmployeeID());
            }
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
    /**
     * @return the accountID
     */
    public String getAccountID() {
        return accountID;
    }

    /**
     * @param accountID the accountID to set
     */
    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * @return the emp
     */
    public Employee getEmp() {
        return emp;
    }

    /**
     * @param emp the emp to set
     */
    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    /**
     * @return the listBR
     */
    public Set<BorrowReturn> getListBR() {
        return listBR;
    }

    /**
     * @param listBR the listBR to set
     */
    public void setListBR(Set<BorrowReturn> listBR) {
        this.listBR = listBR;
    }
    
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
