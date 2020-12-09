/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.BorrowReturn;
import com.ndtk.pojo.Card;
import com.ndtk.service.CardService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ACER
 */

@ManagedBean(name = "readerdetailBean")
@RequestScoped
public class ReaderDetailBean {
    private ResourceBundle bundle = ResourceBundle.getBundle("book", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    
    private static CardService cardSvc = new CardService();
    
    private static String cardID;
    private static String readerName = "";
    private static String email = "";
    private static String phone = "";
    private static Date createDate = null;
    private static Date dueDate = null;
    private static Set<BorrowReturn> listBR;
    
    private String alert = "";
    
    public ReaderDetailBean(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        
        if (!FacesContext.getCurrentInstance().isPostback()) {

            String id = FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap().get("cardID");

            if (id != null && !id.isEmpty()){
                Pattern pattern = Pattern.compile("^[C.0-9]*$");
                Matcher matcher = pattern.matcher(id);
                
                if (matcher.matches()) {
                    Card c = cardSvc.getCardByID(id);
                    if (c != null) {
                        this.setCardID(c.getCardID());
                        this.setReaderName(c.getReader().getReaderName());
                        this.setEmail(c.getReader().getEmail());
                        this.setPhone(c.getReader().getPhone());
                        this.setCreateDate(c.getCreateDate());
                        this.setDueDate(c.getDueDate());
                        this.setListBR(c.getListBorrowReturn());
                    }
                    else
                        FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
                            .handleNavigation(FacesContext.getCurrentInstance(), null, "reader?faces-redirect=true");
                }
                else
                    FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
                            .handleNavigation(FacesContext.getCurrentInstance(), null, "reader?faces-redirect=true");
            }
            else
                FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
                        .handleNavigation(FacesContext.getCurrentInstance(), null, "reader?faces-redirect=true");
        }
    }

    public void updateReader(){
        Card c = cardSvc.getCardByID(this.getCardID());
        
        // Reader Name
        if (!this.getReaderName().trim().equals("")) {
            c.getReader().setReaderName(this.getReaderName());
        }
        else{
           this.setAlert(this.bundle.getString("employeedetail.fullName") + " " +
                    this.bundle.getString("bookdetail.validateBlank"));
           return;
        }
        
        // Email
        if (!this.getEmail().trim().equals("")) {
            c.getReader().setEmail(this.getEmail());
        }
        else{
           this.setAlert(this.bundle.getString("bookborrow.email") + " " +
                    this.bundle.getString("bookdetail.validateBlank"));
           return;
        }
        
        // Phone
        if (!this.getPhone().trim().equals("")) {
            c.getReader().setPhone(this.getPhone());
        }
        else{
           this.setAlert(this.bundle.getString("bookborrow.phone") + " " +
                    this.bundle.getString("bookdetail.validateBlank"));
           return;
        }
        
        // Due Date
        if (this.getDueDate().before(c.getCreateDate()) || this.getDueDate().before(c.getDueDate())) {
            this.alert = this.bundle.getString("reader.dueDateCheck");
            return;
        }
        else
            c.setDueDate(new java.sql.Date(this.getDueDate().getTime()));
        
        if (cardSvc.addOrSaveCard(c)) {
            this.alert = "";
            FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
                .handleNavigation(FacesContext
                        .getCurrentInstance(), null, "reader-detail?faces-redirect=true&cardID=" + c.getCardID());
        }
    }
    
    public void deleteBook(){
        Card c = cardSvc.getCardByID(this.getCardID());
        
        List<BorrowReturn> listBR = new ArrayList<>(c.getListBorrowReturn());
        List<BorrowReturn> listBRFilter = listBR.stream()
                .filter(p -> p.getReturnDate() == null).collect(Collectors.toList());
        
        if (listBRFilter.size() > 0) {
            this.alert = this.bundle.getString("reader.deleteCheck");
            return;
        }
        
        if (cardSvc.deleteCard(c)) {
            this.alert = "";
            FacesContext context = FacesContext.getCurrentInstance();
            context.getApplication().getNavigationHandler()
                .handleNavigation(context, null, "reader?faces-redirect=true");
        }
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
     * @return the readerSvc
     */
    public static CardService getCardSvc() {
        return cardSvc;
    }

    /**
     * @param aReaderSvc the readerSvc to set
     */
    public static void setCardSvc(CardService cardSvc) {
        cardSvc = cardSvc;
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
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
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
    // </editor-fold>
}
