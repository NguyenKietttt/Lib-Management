/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.Book;
import com.ndtk.pojo.BorrowReturn;
import com.ndtk.pojo.BorrowReturnDetail;
import com.ndtk.res.BookReturnRes;
import com.ndtk.service.BorrowReturnService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ACER
 */

@ManagedBean(name = "returnBean")
@RequestScoped
public class ReturnBean {
    private ResourceBundle bundle = ResourceBundle.getBundle("book", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    
    private static BorrowReturnService brSvc = new BorrowReturnService();
    
    private static String brID;
    private static String cardID = "";
    private static String readerName = "";
    private static String email = "";
    private static String phone = "";
    private static Date borrowDate = null;
    private static ArrayList<Book> listBook = new ArrayList<Book>();
    private static Map<Integer, Boolean> lostBook = new HashMap<>();
    private static ArrayList<BookReturnRes> listBookReturnRes = new ArrayList<BookReturnRes>();
    
    private String status = "";
    
    public ReturnBean(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        
        brID = "";
    }
    
    public void searchBR(){
        this.readerName = "";
        this.email = "";
        this.phone = "";
        this.borrowDate = null; 
        listBook.clear();
        lostBook.clear();
        listBookReturnRes.clear();
        
        BorrowReturn br = brSvc.getBorrowNotReturnByID(this.brID);
        
        if (br != null) {
            this.cardID = br.getCard().getCardID();
            this.readerName = br.getCard().getReader().getReaderName();
            this.email = br.getCard().getReader().getEmail();
            this.phone = br.getCard().getReader().getPhone();
            this.borrowDate = br.getBorrowDate(); 
            br.getListBorrowReturnDetail().forEach((brDatail) -> {
                listBook.add(brDatail.getBook());
            });
            
            int count = 0;
            for (Book b : listBook) {
                BookReturnRes bRes = new BookReturnRes();
                bRes.setId(count);
                bRes.setBookName(b.getBookName());
                bRes.setBorrowReturnDetailID(br.getListBorrowReturnDetail().get(count).getId());
                
                listBookReturnRes.add(bRes);
                count++;
            }
        }
        else
            this.status = this.bundle.getString("bookreturn.check");
    }
    
    public void returnBook(){
        BorrowReturn br = brSvc.getBorrowReturnByID(this.brID);
        
        if (br != null) {
            // Return Date
            LocalDate oldDate = br.getBorrowDate().toLocalDate();
            
            LocalDate newDate = LocalDate.now();
            
            java.sql.Date returnDate = java.sql.Date.valueOf(newDate);
            br.setReturnDate(returnDate);
            
            // Fines
            long daysLate = ChronoUnit.DAYS.between(oldDate, newDate);
            if (daysLate > 30) {
                long late = daysLate - 30;
                BigDecimal fines = BigDecimal.valueOf(late * 5000);
                br.setFines(fines);
            }
            
            // Lost Book
            for (Iterator<BookReturnRes> iter = listBookReturnRes.iterator(); iter.hasNext(); ){
                BookReturnRes brRes = iter.next();
                if (lostBook.get(brRes.getId()) == false) {
                    iter.remove();
                }
            }
            
            List<BorrowReturnDetail> listBookLost = br.getListBorrowReturnDetail();
            
            for (BorrowReturnDetail brDetail : listBookLost) {
                for (BookReturnRes brRes : listBookReturnRes) {
                    if (brRes.getBorrowReturnDetailID() == brDetail.getId()) {
                        brDetail.setLost(true);
                        break;
                    }
                }
            }
            
            br.setListBorrowReturnDetail(listBookLost);
            
            if (brSvc.addOrSaveBorrowReturn(br)) {
                this.status = "";
                
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getSessionMap().put("brID", br.getBorrowReturnID());
                context.getApplication().getNavigationHandler()
                        .handleNavigation(context, null, "book-return-detail?faces-redirect=true");
            }
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
    
    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the lostBook
     */
    public Map<Integer, Boolean> getLostBook() {
        return lostBook;
    }

    /**
     * @param lostBook the lostBook to set
     */
    public void setLostBook(Map<Integer, Boolean> lostBook) {
        this.lostBook = lostBook;
    }

    /**
     * @return the listBookRes
     */
    public ArrayList<BookReturnRes> getListBookReturnRes() {
        return listBookReturnRes;
    }

    /**
     * @param aListBookRes the listBookRes to set
     */
    public void setListBookReturnRes(ArrayList<BookReturnRes> aListBookRes) {
        listBookReturnRes = aListBookRes;
    }
    // </editor-fold>
}
