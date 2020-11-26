/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.pojo;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
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
@Table(name = "Category")
public class Category implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryID;
    
    @Column(name = "CategoryName")
    private String categoryName;
    
    @OneToMany(mappedBy = "category")
    private Set<Book> listBook;
    
    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
    /**
     * @return the categoryId
     */
    public int getCategoryId() {
        return categoryID;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(int categoryId) {
        this.categoryID = categoryId;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
