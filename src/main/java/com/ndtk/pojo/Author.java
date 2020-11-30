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
@Table(name = "Author")
public class Author implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authorID;
    
    @Column(name = "AuthorName")
    private String authorName;
    
    @OneToMany(mappedBy = "author")
    private Set<Book> listBook;
    
    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
    /**
     * @return the AuthorID
     */
    public int getAuthorID() {
        return authorID;
    }

    /**
     * @param AuthorID the AuthorID to set
     */
    public void setAuthorID(int AuthorID) {
        this.authorID = AuthorID;
    }

    /**
     * @return the AuthorName
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * @param AuthorName the AuthorName to set
     */
    public void setAuthorName(String AuthorName) {
        this.authorName = AuthorName;
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
