/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.bean;

import com.ndtk.pojo.Employee;
import com.ndtk.service.EmployeeService;
import java.util.ArrayList;
import java.util.TimeZone;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ACER
 */

@ManagedBean(name = "employeeBean")
@RequestScoped
public class EmployeeBean {
    private static EmployeeService employeeSvc = new EmployeeService();
    
    private static ArrayList<Employee> listEmp = new ArrayList<>();
    
    private String keyword;

    public EmployeeBean(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        
        if (!FacesContext.getCurrentInstance().isPostback()) {
            listEmp = employeeSvc.getListEmployee(); 
        }
    }
    
    public void filterEmployee(){
        String temp = this.getKeyword().trim().replaceAll("\\s+", " ");
        
        if (temp == null || temp.equals("")) {
            return;
        }
        
        ArrayList<Employee> listE = employeeSvc.filterEmployee(this.keyword);
        
        if (listE == null) {
            listEmp.clear();
            return;
        }
        
        listEmp = listE;
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Getter - Setter ">
    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    /**
     * @return the listEmp
     */
    public ArrayList<Employee> getListEmp() {
        return listEmp;
    }

    /**
     * @param listEmp the listEmp to set
     */
    public void setListEmp(ArrayList<Employee> listEmp) {
        this.listEmp = listEmp;
    }
    
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
