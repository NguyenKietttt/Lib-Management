/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.pojo;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ACER
 */

@Entity
@Table(name = "Book")
public class Book implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookID;
    
    @Column(name = "BookName")
    private String bookName;
    
    @Column(name = "PublishingDate")
    private Date publishingDate;
    
    @Column(name = "Image")
    private String image;
    
    @Column(name = "BookDescription")
    private String bookDescription;
    
    @Column(name = "BookStatus")
    private boolean bookStatus;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryID")
    private Category category;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AuthorID")
    private Author author;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PublisherID")
    private Publisher publisher;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<BorrowReturnDetail> listBorrowReturnDetail;
        
    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
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
     * @return the Publisher
     */
    public Publisher getPublisher() {
        return publisher;
    }

    /**
     * @param Publisher the Publisher to set
     */
    public void setPublisher(Publisher Publisher) {
        this.publisher = Publisher;
    }
    /**
     * @return the Image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param Image the Image to set
     */
    public void setImage(String Image) {
        this.image = Image;
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
    public void bookBookStatus(boolean bookStatus) {
        this.bookStatus = bookStatus;
    }
    
    /**
     * @return the listBorrowReturnDetail
     */
    public Set<BorrowReturnDetail> getListBorrowReturnDetail() {
        return listBorrowReturnDetail;
    }

    /**
     * @param listBorrowReturnDetail the listBorrowReturnDetail to set
     */
    public void setListBorrowReturnDetail(Set<BorrowReturnDetail> listBorrowReturnDetail) {
        this.listBorrowReturnDetail = listBorrowReturnDetail;
    }
    // </editor-fold>
}
