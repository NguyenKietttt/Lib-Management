/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.Author;
import com.ndtk.service.AuthorService;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ACER
 */

@ManagedBean(name = "authorBean")
@SessionScoped
public class AuthorBean {
    private static AuthorService authorSvc = new AuthorService();
    
    public AuthorBean(){
        
    }
    
    public ArrayList<Author> getListAuthor(){
        return authorSvc.getAllAuthor();
    }

    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
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
    // </editor-fold>
}
