/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.Book;
import com.ndtk.pojo.BorrowReturn;
import com.ndtk.pojo.BorrowReturnDetail;
import com.ndtk.pojo.Employee;
import com.ndtk.service.BorrowReturnService;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ACER
 */

@ManagedBean(name = "borrowdetailBean")
@RequestScoped
public class BorrowDetailBean {
    private static BorrowReturnService brSvc = new BorrowReturnService();
    
    private String brID = "";
    private String cardID = "";
    private String readerName = "";
    private String email = "";
    private String phone = "";
    private Date borrowDate = null;
    private Employee employee = null;
    private ArrayList<Book> listBook = new ArrayList<Book>();
    
    
    public BorrowDetailBean(){
        FacesContext context = FacesContext.getCurrentInstance();
        
        String brID = (String) context.getExternalContext().getSessionMap().get("brID");
        BorrowReturn br = brSvc.getBorrowReturnByID(brID);
        
        if (br != null) {
            this.brID = br.getBorrowReturnID();
            this.cardID = br.getCard().getCardID();
            this.readerName = br.getCard().getReader().getReaderName();
            this.email = br.getCard().getReader().getEmail();
            this.phone = br.getCard().getReader().getPhone();
            this.borrowDate = br.getBorrowDate(); 
            this.employee = br.getEmployee();
            for (BorrowReturnDetail brDetail : br.getListBorrowReturnDetail()) {
                listBook.add(brDetail.getBook());
            }
        }
        else
            context.getApplication().getNavigationHandler()
                    .handleNavigation(context, null, "book?faces-redirect=true");
    }
    
    public void confirmBorrow(){
        FacesContext context = FacesContext.getCurrentInstance();
        
        context.getExternalContext().getSessionMap().remove("brID");
        
        context.getApplication().getNavigationHandler()
                .handleNavigation(context, null, "book?faces-redirect=true");
    }

    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
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
     * @return the brSvc
     */
    public static BorrowReturnService getBrSvc() {
        return brSvc;
    }

    /**
     * @param aBrSvc the brSvc to set
     */
    public static void setBrSvc(BorrowReturnService aBrSvc) {
        brSvc = aBrSvc;
    }

    /**
     * @return the cardID
     */
    public String getCardID() {
        return cardID;
    }

    /**
     * @param cardID the cardID to set
     */
    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    /**
     * @return the readerName
     */
    public String getReaderName() {
        return readerName;
    }

    /**
     * @param readerName the readerName to set
     */
    public void setReaderName(String readerName) {
        this.readerName = readerName;
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
     * @return the listBook
     */
    public ArrayList<Book> getListBook() {
        return listBook;
    }

    /**
     * @param listBook the listBook to set
     */
    public void setListBook(ArrayList<Book> listBook) {
        this.listBook = listBook;
    }
    // </editor-fold>

    /**
     * @return the brID
     */
    public String getBrID() {
        return brID;
    }

    /**
     * @param brID the brID to set
     */
    public void setBrID(String brID) {
        this.brID = brID;
    }

}
