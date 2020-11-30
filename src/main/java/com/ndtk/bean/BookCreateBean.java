/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.Author;
import com.ndtk.pojo.Book;
import com.ndtk.pojo.Category;
import com.ndtk.pojo.Publisher;
import com.ndtk.service.AuthorService;
import com.ndtk.service.BookService;
import com.ndtk.service.CategoryService;
import com.ndtk.service.PublisherService;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

/**
 *
 * @author ACER
 */

@ManagedBean(name = "bookCreateBean")
@RequestScoped
public class BookCreateBean {
    private static BookService bookSvc = new BookService();
    private static CategoryService categorySvc = new CategoryService();
    private static AuthorService authorSvc = new AuthorService();
    private static PublisherService publisherSvc = new PublisherService();
    
    private static java.util.Date dateUtil;
    private static String bookName;
    private static String image;
    private static String bookDescription;
    private static int cateID;
    private static String authorName;
    private static String publisherName;
    private static String status;
    
    private String alert = "alert";

    private Part part;
    
    public BookCreateBean(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        
        if (!FacesContext.getCurrentInstance().isPostback()){
            this.setDateUtil(null);
            this.setBookName(null);
            this.setImage("default.png");
            this.setBookDescription(null);
            this.setAuthorName(null);
            this.setPublisherName(null);
            this.setStatus(null);
            this.setCateID(0);
        }
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
    
     public void addBook() throws IOException {
        Book b = new Book();
        
        // Book Name
         if (!this.getBookName().trim().equals("")) {
            b.setBookName(this.getBookName());
         }
         else{
            this.setAlert("Book Name cannot be blank");
            return;
         }

        // Book Publishing Date      
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate = format.format(this.getDateUtil());
        Date dateSQL = Date.valueOf(stringDate);
        
        b.setPublishingDate(dateSQL);
        
        // Book Image
        if (this.part != null) {
            this.uploadImg();
            
            b.setImage(this.getPart().getSubmittedFileName());
        }
        else{
            this.setAlert("Image cannot be blank");
            return;
        }
        
        // Book Description
         if (!this.getBookDescription().trim().equals("")) {
            b.setBookDescription(this.getBookDescription());
         }
         else{
            this.setAlert("Book Description cannot be blank");
            return;
         }
        
        // Book Category
        Category cateNew = categorySvc.getCategoryByID(cateID);
        b.setCategory(cateNew);
        
        // Book Author
        if (!this.getAuthorName().trim().equals("")) {
            Author authorNew = new Author();
            Author authorOld = new Author();
        
            authorOld = authorSvc.getAuthorByName(this.getAuthorName());
            if (authorOld == null) {
                authorNew.setAuthorName(this.getAuthorName());

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
        if (!this.getPublisherName().trim().equals("")) {
            Publisher publisherNew = new Publisher();
            Publisher publisherOld = new Publisher();

            publisherOld = publisherSvc.getPublisherByName(this.getPublisherName());
            if (publisherOld == null) {
                publisherNew.setPublisherName(this.getPublisherName());

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
        if (!this.getStatus().trim().equals("")) {
            if (this.getStatus().equals("Còn"))
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
                .handleNavigation(context, null, "book?faces-redirect=true");
        }
    }
    
    public String getMessage() {
      return alert;
   }
    
    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
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
     * @return the bookName
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * @param bookName the bookName to set
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the bookDescription
     */
    public String getBookDescription() {
        return bookDescription;
    }

    /**
     * @param bookDescription the bookDescription to set
     */
    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    /**
     * @return the authorName
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * @param authorName the authorName to set
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     * @return the publisherName
     */
    public String getPublisherName() {
        return publisherName;
    }

    /**
     * @param publisherName the publisherName to set
     */
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
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
     * @return the dateUtil
     */
    public java.util.Date getDateUtil() {
        return dateUtil;
    }

    /**
     * @param dateUtil the dateUtil to set
     */
    public void setDateUtil(java.util.Date dateUtil) {
        this.dateUtil = dateUtil;
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
    // </editor-fold>
}
