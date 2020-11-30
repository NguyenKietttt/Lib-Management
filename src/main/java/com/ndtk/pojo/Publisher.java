/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.pojo;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ACER
 */

@Entity
@Table(name = "Publisher")
public class Publisher implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int publisherID;
    
    @Column(name = "PublisherName")
    private String publisherName;
    
    @OneToMany(mappedBy = "publisher")
    private Set<Book> listBook;
    
    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
    /**
     * @return the PublisherID
     */
    public int getPublisherID() {
        return publisherID;
    }

    /**
     * @param PublisherID the PublisherID to set
     */
    public void setPublisherID(int PublisherID) {
        this.publisherID = PublisherID;
    }

    /**
     * @return the PublisherName
     */
    public String getPublisherName() {
        return publisherName;
    }

    /**
     * @param PublisherName the PublisherName to set
     */
    public void setPublisherName(String PublisherName) {
        this.publisherName = PublisherName;
    }
    
        /**
     * @return the listBook
     */
    public Set<Book> getListBook() {
        return listBook;
    }

    /**
     * @param listBook the listBook to set
     */
    public void setListBook(Set<Book> listBook) {
        this.listBook = listBook;
    }
    // </editor-fold>
}
