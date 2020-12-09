/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.Account;
import com.ndtk.pojo.Book;
import com.ndtk.pojo.BorrowReturn;
import com.ndtk.pojo.BorrowReturnDetail;
import com.ndtk.pojo.Card;
import com.ndtk.pojo.Reader;
import com.ndtk.service.BookService;
import com.ndtk.service.BorrowReturnService;
import com.ndtk.service.CardService;
import com.ndtk.service.ReaderService;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ACER
 */

@ManagedBean(name="readerBean")
@RequestScoped
public class ReaderBean {
    private ResourceBundle bundle = ResourceBundle.getBundle("book", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    
    private static ReaderService readerSvc = new ReaderService();
    private static CardService cardSvc = new CardService();
    private static BookService bookSvc = new BookService();
    private static BorrowReturnService brSvc = new BorrowReturnService();
    
    private static int bookBorrow;
    private static ArrayList<Reader> listReader;
    
    private String cardID;
    private String readerName = "";
    private String email = "";
    private String phone = "";
    private String status = "";
    private String keyword;
    
    public ReaderBean(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        
        if (!FacesContext.getCurrentInstance().isPostback()) {
            listReader = readerSvc.getListReader(); 
        }
    }
    
    public void searchReader(){
        Reader r = getReaderSvc().getReaderByCardID(cardID);
        
        if (r == null) {
            this.status = this.bundle.getString("card.null");
            return;
        }
        
        java.util.Date dueDate = r.getCard().getDueDate();
        java.util.Date currentDate = new java.util.Date();
        if (dueDate.before(currentDate)) {
            this.status = this.bundle.getString("card.expire");
            return;
        }

        Card c = cardSvc.getCardByID(r.getCard().getCardID());
        Set<BorrowReturn> listBR = c.getListBorrowReturn();
        List<BorrowReturn> listFilter = listBR.stream().filter(
                p -> p.getReturnDate() == null).collect(Collectors.toList());
        
        this.bookBorrow = 5;
        
        if (listFilter.size() > 0) {
            for (BorrowReturn br : listBR) {
                if (br.getListBorrowReturnDetail().size() > 5) {
                    this.status = this.bundle.getString("bookborrow.borrowInMonth");
                    return;
                }
                else
                    this.bookBorrow -=  br.getListBorrowReturnDetail().size();
            } 
        }
        
        Map<Integer, Object> cart = (Map<Integer, Object>) FacesContext.getCurrentInstance()
            .getExternalContext().getSessionMap().get("cart");
        
        if (cart.size() < 1) {
            this.status = this.bundle.getString("bookborrow.empty");
            return;
        }
        
        int countBook = this.bookBorrow;
        for (Object o : cart.values()) {
            Map<String, Object> b = (Map<String, Object>) o;
            
            int temp = (int) b.get("count");
            countBook -= temp;
            
            if (this.bookBorrow == 0) {
                this.status = this.bundle.getString("bookborrow.borrowInMonth");
                return;
            }
            else
                if (countBook < 0) {
                    this.status = this.bundle.getString("bookborrow.borrowStill") + " " + this.bookBorrow;
                    return;
                }
        }
        
        this.setReaderName(r.getReaderName());
        this.setEmail(r.getEmail());
        this.setPhone(r.getPhone());
    }

    public void borrowBook(){
        FacesContext context = FacesContext.getCurrentInstance();
        
        Map<Integer, Object> cart = (Map<Integer, Object>) context.getExternalContext()
                                    .getSessionMap().get("cart");
        
        if (cart.size() < 1) {
            this.status = this.bundle.getString("bookborrow.empty");
            return;
        }
        
        BorrowReturn br = new BorrowReturn();
        
        // ID
        int id = getBrSvc().getBorrowReturnID() + 1;
        br.setBorrowReturnID("BR" + String.valueOf(id));
        
        // CardID
        Card c = cardSvc.getCardByID(this.cardID);
        br.setCard(c);
        
        // EmployeeID
        Account acc = (Account) context.getExternalContext().getSessionMap().get("user");
        br.setEmployee(acc.getEmployee());
        
        // BorrowDate
        Date date = new Date(java.util.Calendar.getInstance().getTime().getTime());
        br.setBorrowDate(date);
        
        // List BorrowReturnDetail
        ArrayList<BorrowReturnDetail> listBRD = new ArrayList<>();
        for (Object o : cart.values()) {
            Map<String, Object> bMap = (Map<String, Object>) o;
            
            if ((int) bMap.get("count") > 1) {
                for (int i = 0; i < (int) bMap.get("count"); i++) {
                    BorrowReturnDetail brd = new BorrowReturnDetail();
                    Book b = bookSvc.getBookByID((int) bMap.get("bookID"));
                    
                    brd.setBorrowReturn(br);
                    brd.setBook(b);
                    
                    listBRD.add(brd);
                }
            }
            else{
                BorrowReturnDetail brd = new BorrowReturnDetail();
                Book b = bookSvc.getBookByID((int) bMap.get("bookID"));
                
                brd.setBorrowReturn(br);
                brd.setBook(b);
                
                listBRD.add(brd);
            }
        }
        
        br.setListBorrowReturnDetail(listBRD);
        
        if (getBrSvc().addOrSaveBorrowReturn(br)) {
            this.status = "";
            cart.clear();
            
            context.getExternalContext().getSessionMap().put("brID", br.getBorrowReturnID());
            context.getApplication().getNavigationHandler()
                    .handleNavigation(context, null, "book-borrow-detail?faces-redirect=true");
        }
    }
    
    public void filterReader(){
        String temp = this.getKeyword().trim().replaceAll("\\s+", " ");
        
        if (temp == null || temp.equals("")) {
            return;
        }
        
        ArrayList<Reader> listR = getReaderSvc().filterReader(this.keyword);
        
        if (listR == null) {
            listReader.clear();
            return;
        }
        
        listReader = listR;
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
     * @return the readerSvc
     */
    public static ReaderService getReaderSvc() {
        return readerSvc;
    }

    /**
     * @param aReaderSvc the readerSvc to set
     */
    public static void setReaderSvc(ReaderService aReaderSvc) {
        readerSvc = aReaderSvc;
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
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    /**
     * @return the listReader
     */
    public ArrayList<Reader> getListReader() {
        return listReader;
    }

    /**
     * @param listReader the listReader to set
     */
    public void setListReader(ArrayList<Reader> listReader) {
        this.listReader = listReader;
    }
    
    /**
     * @return the bookSvc
     */
    public static BookService getBookSvc() {
        return bookSvc;
    }

    /**
     * @param aBookSvc the bookSvc to set
     */
    public static void setBookSvc(BookService aBookSvc) {
        bookSvc = aBookSvc;
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
     * @return the cardSvc
     */
    public static CardService getCardSvc() {
        return cardSvc;
    }

    /**
     * @param aCardSvc the cardSvc to set
     */
    public static void setCardSvc(CardService aCardSvc) {
        cardSvc = aCardSvc;
    }

    /**
     * @return the bookBorrow
     */
    public int getBookBorrow() {
        return bookBorrow;
    }

    /**
     * @param bookBorrow the bookBorrow to set
     */
    public void setBookBorrow(int bookBorrow) {
        this.bookBorrow = bookBorrow;
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
    // </editor-fold>
}
