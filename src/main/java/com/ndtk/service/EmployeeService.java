/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.service;

import com.ndtk.Library_web.HibernateUtil;
import com.ndtk.pojo.Employee;
import java.util.ArrayList;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author ACER
 */

public class EmployeeService {
    private static final SessionFactory factory = HibernateUtil.getFACTORY();
    
    public int getEmployeeID(){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
            Root<Employee> root = query.from(Employee.class);
            
            query.select(root);
            
            ArrayList<Employee> listBook = (ArrayList<Employee>) session.createQuery(query).getResultList();
            
            if (listBook.size() > 0) {
                return listBook.size();
            }
           
            return 1;
        }
    }
    
    public boolean addOrSaveEmployee(Employee employee){
        try(Session session = factory.openSession()){
            try{
                session.getTransaction().begin();

                session.saveOrUpdate(employee);

                session.getTransaction().commit();
            }
            catch(Exception ex){
                session.getTransaction().rollback();
                return false;
            }
        }

        return true;
    }
    
    public boolean DeleteEmployee(Employee employee){
        try(Session session = factory.openSession()){
            try{
                session.getTransaction().begin();

                session.delete(employee);

                session.getTransaction().commit();
            }
            catch(Exception ex){
                session.getTransaction().rollback();
                return false;
            }
        }

        return true;
    }
}
