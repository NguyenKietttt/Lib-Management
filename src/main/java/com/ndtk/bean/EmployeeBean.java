/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.Employee;
import com.ndtk.service.EmployeeService;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author ACER
 */

@ManagedBean(name = "employeeBean")
@RequestScoped
public class EmployeeBean {
    private static EmployeeService employeeSvc = new EmployeeService();

    public EmployeeBean(){
        
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
    /**
     * @return the employeeSvc
     */
    public static EmployeeService getEmployeeSvc() {
        return employeeSvc;
    }

    /**
     * @param aEmployeeSvc the employeeSvc to set
     */
    public static void setEmployeeSvc(EmployeeService aEmployeeSvc) {
        employeeSvc = aEmployeeSvc;
    }
    // </editor-fold>
}
