/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.Publisher;
import com.ndtk.service.PublisherService;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ACER
 */

@ManagedBean(name = "publisherBean")
@SessionScoped
public class PublisherBean {
    private static PublisherService publisherSvc = new PublisherService();
    
    public PublisherBean(){
        
    }
    
    public ArrayList<Publisher> getListPublisher(){
        return publisherSvc.getAllPublisher();
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
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
    // </editor-fold>
}
