/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.BorrowReturn;
import com.ndtk.pojo.BorrowReturnDetail;
import com.ndtk.pojo.Employee;
import com.ndtk.res.BookReturnDetailRes;
import com.ndtk.service.BorrowReturnService;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ACER
 */

@ManagedBean(name = "returndetailBean")
@RequestScoped
public class ReturnDetailBean {
    private ResourceBundle bundle = ResourceBundle.getBundle("book", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    
    private static BorrowReturnService brSvc = new BorrowReturnService();
    
    private String brID;
    private String cardID = "";
    private String readerName = "";
    private String email = "";
    private String phone = "";
    private String fines;
    private Date borrowDate = null;
    private Date returnDate = null;
    private Employee employee = null;
    private ArrayList<BookReturnDetailRes> listBRDRes = new ArrayList<>();
    
    public ReturnDetailBean(){
        
        if (!FacesContext.getCurrentInstance().isPostback()){
            FacesContext context = FacesContext.getCurrentInstance();
            BorrowReturn br;
            String brID;
        
            if (context.getExternalContext().getSessionMap().get("brID") != null)
                brID = (String) context.getExternalContext().getSessionMap().get("brID");
            else{
                brID = (String) context.getExternalContext().getRequestParameterMap().get("brID");

                Pattern pattern = Pattern.compile("^[BR.0-9]*$");
                Matcher matcher = pattern.matcher(brID);

                if (!matcher.matches()) {
                    context.getApplication().getNavigationHandler()
                        .handleNavigation(context, null, "book?faces-redirect=true");
                }
            }
        
            br = brSvc.getBorrowReturnByID(brID);

            if (br != null) {
                this.brID = br.getBorrowReturnID();
                this.cardID = br.getCard().getCardID();
                this.readerName = br.getCard().getReader().getReaderName();
                this.email = br.getCard().getReader().getEmail();
                this.phone = br.getCard().getReader().getPhone();
                if (this.fines == null)
                    this.fines = 0 + " " + this.bundle.getString("bookreturndetail.money");
                else
                    this.fines = br.getFines() + " " + this.bundle.getString("bookreturndetail.money");
                this.borrowDate = br.getBorrowDate(); 
                this.returnDate = br.getReturnDate();
                this.employee = br.getEmployee();
                for (BorrowReturnDetail brDetail : br.getListBorrowReturnDetail()) {
                    BookReturnDetailRes brdRes = new BookReturnDetailRes();
                    brdRes.setBookName(brDetail.getBook().getBookName());
                    if (brDetail.isLost())
                        brdRes.setStatus(this.bundle.getString("bookreturndetail.isLost"));
                    else
                        brdRes.setStatus(this.bundle.getString("bookreturndetail.isStill"));

                    listBRDRes.add(brdRes);
                }
            }
            else
                context.getApplication().getNavigationHandler()
                        .handleNavigation(context, null, "book?faces-redirect=true");
        }
    }
    
    public void confirmReturn(){
        FacesContext context = FacesContext.getCurrentInstance();
        
        if (context.getExternalContext().getSessionMap().get("brID") != null) {
            context.getExternalContext().getSessionMap().remove("brID");
        }

        
        context.getApplication().getNavigationHandler()
                .handleNavigation(context, null, "book-return?faces-redirect=true");
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
     * @return the fines
     */
    public String getFines() {
        return fines;
    }

    /**
     * @param fines the fines to set
     */
    public void setFines(String fines) {
        this.fines = fines;
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
     * @return the returnDate
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * @param returnDate the returnDate to set
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * @return the listBRDRes
     */
    public ArrayList<BookReturnDetailRes> getListBRDRes() {
        return listBRDRes;
    }

    /**
     * @param listBRDRes the listBRDRes to set
     */
    public void setListBRDRes(ArrayList<BookReturnDetailRes> listBRDRes) {
        this.listBRDRes = listBRDRes;
    }
    // </editor-fold>
}
