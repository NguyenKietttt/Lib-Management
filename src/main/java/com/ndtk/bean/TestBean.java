/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author ACER
 */
@ManagedBean(name = "testBean")
@RequestScoped
public class TestBean implements Serializable{
    private boolean check = true;
    
    public TestBean(){
        
    }
    
    public void doCheck(){
        this.check = false;
    }
    

    /**
     * @return the check
     */
    public boolean isCheck() {
        return check;
    }

    /**
     * @param check the check to set
     */
    public void setCheck(boolean check) {
        this.check = check;
    }
}
