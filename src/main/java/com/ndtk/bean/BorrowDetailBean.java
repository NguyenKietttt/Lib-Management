/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.Book;
import com.ndtk.pojo.BorrowReturn;
import com.ndtk.pojo.BorrowReturnDetail;
import com.ndtk.service.BorrowReturnService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    
    private String CardID = "";
    private String ReaderName = "";
    private String Email = "";
    private String Phone = "";
    private Date borrowDate = null;
    private ArrayList<Book> listBook = new ArrayList<Book>();
    
    
    public BorrowDetailBean(){
        FacesContext context = FacesContext.getCurrentInstance();
        
        String brID = (String) context.getExternalContext().getSessionMap().get("brID");

        BorrowReturn br = brSvc.getBorrowReturnByID(brID);
        if (br != null) {
            this.CardID = br.getCard().getCardID();
            this.ReaderName = br.getCard().getReader().getReaderName();
            this.Email = br.getCard().getReader().getEmail();
            this.Phone = br.getCard().getReader().getPhone();
            this.borrowDate = br.getBorrowDate(); 
            for (BorrowReturnDetail brDatail : br.getListBorrowReturnDetail()) {
                listBook.add(brDatail.getBook());
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
     * @return the CardID
     */
    public String getCardID() {
        return CardID;
    }

    /**
     * @param CardID the CardID to set
     */
    public void setCardID(String CardID) {
        this.CardID = CardID;
    }

    /**
     * @return the ReaderName
     */
    public String getReaderName() {
        return ReaderName;
    }

    /**
     * @param ReaderName the ReaderName to set
     */
    public void setReaderName(String ReaderName) {
        this.ReaderName = ReaderName;
    }

    /**
     * @return the Email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * @param Email the Email to set
     */
    public void setEmail(String Email) {
        this.Email = Email;
    }

    /**
     * @return the Phone
     */
    public String getPhone() {
        return Phone;
    }

    /**
     * @param Phone the Phone to set
     */
    public void setPhone(String Phone) {
        this.Phone = Phone;
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
    public List<Book> getListBook() {
        return listBook;
    }

    /**
     * @param listBook the listBook to set
     */
    public void setListBook(ArrayList<Book> listBook) {
        this.listBook = listBook;
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
    // </editor-fold>

}
