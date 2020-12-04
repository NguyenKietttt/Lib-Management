/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.Author;
import com.ndtk.pojo.Book;
import com.ndtk.pojo.BorrowReturnDetail;
import com.ndtk.pojo.Category;
import com.ndtk.pojo.Publisher;
import com.ndtk.res.BookDetailRes;
import com.ndtk.service.AuthorService;
import com.ndtk.service.BookService;
import com.ndtk.service.CategoryService;
import com.ndtk.service.PublisherService;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

/**
 *
 * @author ACER
 */

@ManagedBean(name = "bookDetailBean")
@RequestScoped
public class BookDetailBean {
    private static BookService bookSvc = new BookService();
    private static CategoryService categorySvc = new CategoryService();
    private static AuthorService authorSvc = new AuthorService();
    private static PublisherService publisherSvc = new PublisherService();
    
    private static BookDetailRes bookDetailRes;
    private static java.util.Date dateString;
    
    private String alert = "alert";
    private int cateID;
    private Part part;

    public BookDetailBean() throws IOException, ParseException {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        
        if (bookDetailRes == null ) {
           bookDetailRes = new BookDetailRes();
        }
        
        if (!FacesContext.getCurrentInstance().isPostback()) {

            String id = FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap().get("bookID");

            if (id != null && !id.isEmpty()){
                Pattern pattern = Pattern.compile("^[0-9]*$");
                Matcher matcher = pattern.matcher(id);
                
                if (matcher.matches()) {
                    Book b = bookSvc.getBookByID(Integer.parseInt(id));
                    if (b != null) {
                        bookDetailRes.setBookID(b.getBookID());
                        bookDetailRes.setBookName(b.getBookName());
                        bookDetailRes.setPublishingDate(b.getPublishingDate());
                        bookDetailRes.setImage(b.getImage());
                        bookDetailRes.setBookDescription(b.getBookDescription());
                        bookDetailRes.setBookStatus(b.getBookStatus());
                        bookDetailRes.setCategory(b.getCategory());
                        bookDetailRes.setAuthor(b.getAuthor());
                        bookDetailRes.setPublisher(b.getPublisher());
                        if (b.getBookStatus())
                            bookDetailRes.setStatus("Còn");
                        else
                            bookDetailRes.setStatus("Hết");
                        
                        java.util.Date newDate = bookDetailRes.getPublishingDate();
                        this.setDateString(newDate);    

                    }
                    else
                        FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
                            .handleNavigation(FacesContext.getCurrentInstance(), null, "book?faces-redirect=true");
                }
                else
                    FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
                            .handleNavigation(FacesContext.getCurrentInstance(), null, "book?faces-redirect=true");
            }
        }
    }
    
    public ArrayList<Category> getListCategoryEdit(){
        FacesContext context = FacesContext.getCurrentInstance();
        Book b;
        
        if (context.getExternalContext().getRequestParameterMap().get("bookID") != null)
            b = bookSvc.getBookByID(Integer.parseInt(
                    context.getExternalContext().getRequestParameterMap().get("bookID")));
        else
            b = bookSvc.getBookByID(bookDetailRes.getBookID());

        ArrayList<Category> list = categorySvc.getAllCategory();
        
        for (Iterator<Category> iter = list.iterator(); iter.hasNext(); ){
            Category c = iter.next();
            
            if (c.getCategoryId() == b.getCategory().getCategoryId()) {
                iter.remove();
            }
        }
        
        return list;
    }
    
    public void uploadImg() throws IOException{
        String path = FacesContext.getCurrentInstance()
            .getExternalContext()
            .getInitParameter("com.ndtk.ImgPath") + "\\" + this.part.getSubmittedFileName();

        try(InputStream in = this.part.getInputStream();
                FileOutputStream out = new FileOutputStream(path);){
            byte[] b = new byte[1024];

            int byteRead;

            while((byteRead = in.read(b)) != -1){
                out.write(b, 0, byteRead);
            }
        }
    }

    public void updateBook() throws IOException {
        Book b = bookSvc.getBookByID(this.getBookDetailRes().getBookID());
        
        if (b.getListBorrowReturnDetail().size() > 0) {
            for (BorrowReturnDetail borrowReturnDetail : b.getListBorrowReturnDetail()) {
                if (borrowReturnDetail.getBorrowReturn().getReturnDate() == null){
                    this.setAlert("Book has been borrowed cannot update!");
                    return;
                }
            }
        }
        
        // Book Name
        if (!bookDetailRes.getBookName().trim().equals("")) {
            b.setBookName(bookDetailRes.getBookName());
         }
         else{
            this.setAlert("Book Name cannot be blank");
            return;
         }
        
        // Book Publishing Date      
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate = format.format(this.getDateString());
        Date dateSQL = Date.valueOf(stringDate);
        
        b.setPublishingDate(dateSQL);
        
        // Book Image
        if (this.part != null) {
            this.uploadImg();
            
            b.setImage(this.getPart().getSubmittedFileName());
        }
        else
            b.setImage(bookDetailRes.getImage());
        
        // Book Description
        if (!bookDetailRes.getBookDescription().trim().equals("")) {
            b.setBookDescription(bookDetailRes.getBookDescription());
         }
         else{
            this.setAlert("Book Description cannot be blank");
            return;
         }
        
        // Book Category
        Category cateNew = categorySvc.getCategoryByID(cateID);
        b.setCategory(cateNew);
        
        // Book Author
        if (!bookDetailRes.getAuthor().getAuthorName().trim().equals("")) {
            Author authorNew = new Author();
            Author authorOld = new Author();

            authorOld = authorSvc.getAuthorByName(bookDetailRes.getAuthor().getAuthorName());
            if (authorOld == null) {
                authorNew.setAuthorName(bookDetailRes.getAuthor().getAuthorName());

                authorSvc.addOrSaveAuthor(authorNew);
            }

            if (authorNew.getAuthorName() == null)
                b.setAuthor(authorOld);
            else
                b.setAuthor(authorNew);
        }
        else{
            this.setAlert("Author cannot be blank");
            return;
        }
        
        // Book Publisher
        if (!bookDetailRes.getPublisher().getPublisherName().trim().equals("")) {
            Publisher publisherNew = new Publisher();
            Publisher publisherOld = new Publisher();

            publisherOld = publisherSvc.getPublisherByName(bookDetailRes.getPublisher().getPublisherName());
            if (publisherOld == null) {
                publisherNew.setPublisherName(bookDetailRes.getPublisher().getPublisherName());

                publisherSvc.addOrSavePubliser(publisherNew);
            }

            if (publisherNew.getPublisherName() == null)
                b.setPublisher(publisherOld);
            else
                b.setPublisher(publisherNew);
        }
        else{
            this.setAlert("Publisher cannot be blank");
            return;
        }
        
        
        // Book Status
        if (!bookDetailRes.getStatus().trim().equals("")) {
            if (bookDetailRes.getStatus().equals("Còn"))
                b.setBookStatus(true);
            else
                b.setBookStatus(false);
        }
        else{
            this.setAlert("Book Status cannot be blank");
            return;
        }
            
        // Update
        if (bookSvc.addOrSaveBook(b)) {
            this.setAlert("");
            FacesContext context = FacesContext.getCurrentInstance();
            context.getApplication().getNavigationHandler()
                .handleNavigation(context, null, "book-detail?faces-redirect=true&bookID=" + bookDetailRes.getBookID());
        }
        else
            this.setAlert("Cannot be update");
    }
    
    public void deleteBook(){
        Book b = bookSvc.getBookByID(this.getBookDetailRes().getBookID());
        
        if (b.getListBorrowReturnDetail().size() > 0) {
            for (BorrowReturnDetail borrowReturnDetail : b.getListBorrowReturnDetail()) {
                if (borrowReturnDetail.getBorrowReturn().getReturnDate() == null){
                    this.setAlert("Book has been borrowed cannot be delete");
                    return;
                }
            }
        }
        
        if (bookSvc.deleteBook(b)) {
            this.setAlert("");
            FacesContext context = FacesContext.getCurrentInstance();
            context.getApplication().getNavigationHandler()
                .handleNavigation(context, null, "book?faces-redirect=true");
        }
        else
            this.setAlert("Cannot be delete");
    }

    public String getMessage() {
      return alert;
   }
        
    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
    /**
     * @return the part
     */
    public Part getPart() {
        return part;
    }

    /**
     * @param part the part to set
     */
    public void setPart(Part part) {
        this.part = part;
    }
    
    /**
     * @return the dateString
     */
    public java.util.Date getDateString() {
        return dateString;
    }

    /**
     * @param dateString the dateString to set
     */
    public void setDateString(java.util.Date dateString) {
        this.dateString = dateString;
    }
    
    /**
     * @return the authorSvc
     */
    public static AuthorService getAuthorSvc() {
        return authorSvc;
    }

    /**
     * @param aAuthorSvc the authorSvc to set
     */
    public static void setAuthorSvc(AuthorService aAuthorSvc) {
        authorSvc = aAuthorSvc;
    }

    /**
     * @return the publisherSvc
     */
    public static PublisherService getPublisherSvc() {
        return publisherSvc;
    }

    /**
     * @param aPublisherSvc the publisherSvc to set
     */
    public static void setPublisherSvc(PublisherService aPublisherSvc) {
        publisherSvc = aPublisherSvc;
    }
    
     /**
     * @return the cateID
     */
    public int getCateID() {
        return cateID;
    }

    /**
     * @param cateID the cateID to set
     */
    public void setCateID(int cateID) {
        this.cateID = cateID;
    }
    
    /**
     * @return the categorySvc
     */
    public static CategoryService getCategorySvc() {
        return categorySvc;
    }

    /**
     * @param aCategorySvc the categorySvc to set
     */
    public static void setCategorySvc(CategoryService aCategorySvc) {
        categorySvc = aCategorySvc;
    }
    
    /**
     * @return the bookDetailRes
     */
    public BookDetailRes getBookDetailRes() {
        return bookDetailRes;
    }

    /**
     * @param bookDetailRes the bookDetailRes to set
     */
    public void setBookDetailRes(BookDetailRes bookDetailRes) {
        this.bookDetailRes = bookDetailRes;
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
    // </editor-fold>
}
