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
import com.ndtk.service.BookService;
import java.io.IOException;
import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ACER
 */

@ManagedBean(name = "bookDetailBean")
@RequestScoped
public class BookDetailBean {
    private static BookService bookSvc = new BookService();
    private static int bookID;
    
    private String bookName;
    private Date publishingDate;
    private String image;
    private String bookDescription;
    private boolean bookStatus;
    private Category category;
    private Author author;
    private Publisher publisher;
    private String status;
    
    public BookDetailBean() throws IOException{   
        if (!FacesContext.getCurrentInstance().isPostback()) {
            String id = FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap().get("bookID");
            
            if (id != null && !id.isEmpty()){
                Pattern pattern = Pattern.compile("^[0-9]*$");
                Matcher matcher = pattern.matcher(id);
                
                if (matcher.matches()) {
                    Book b = bookSvc.getBookByID(Integer.parseInt(id));
                    if (b != null) {
                        this.setBookID(b.getBookID());
                        this.setBookID(b.getBookID());
                        this.setBookName(b.getBookName());
                        this.setPublishingDate(b.getPublishingDate());
                        this.setImage(b.getImage());
                        this.setBookDescription(b.getBookDescription());
                        this.setBookStatus(b.getBookStatus());
                        this.setCategory(b.getCategory());
                        this.setAuthor(b.getAuthor());
                        this.setPublisher(b.getPublisher());
                        if (b.getBookStatus())
                            this.setStatus("Còn");
                        else
                            this.setStatus("Hết");
                    }
                    else
                        FacesContext.getCurrentInstance()
                                    .getExternalContext().redirect("book.xhtml");
                }
                else
                    FacesContext.getCurrentInstance()
                                    .getExternalContext().redirect("book.xhtml");
            }
        }
    }
    
    public void updateBook(){
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage("Successful",  "Your message: "));
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
     * @return the bookID
     */
    public int getBookID() {
        return bookID;
    }

    /**
     * @param bookID the bookID to set
     */
    public void setBookID(int bookID) {
        this.bookID = bookID;
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
     * @return the publishingDate
     */
    public Date getPublishingDate() {
        return publishingDate;
    }

    /**
     * @param publishingDate the publishingDate to set
     */
    public void setPublishingDate(Date publishingDate) {
        this.publishingDate = publishingDate;
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
     * @return the quantity
     */
    public boolean getBookStatus() {
        return bookStatus;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setBookStatus(boolean bookStatus) {
        this.bookStatus = bookStatus;
    }

    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @return the author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * @return the publisher
     */
    public Publisher getPublisher() {
        return publisher;
    }

    /**
     * @param publisher the publisher to set
     */
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
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
    // </editor-fold>
}
