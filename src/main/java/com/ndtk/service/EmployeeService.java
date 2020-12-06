/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.service;

import com.ndtk.Library_web.HibernateUtil;
import com.ndtk.pojo.Employee;
import com.ndtk.pojo.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    
    public ArrayList<Employee> filterEmployee(String keyword){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
            Root<Employee> root = query.from(Employee.class);
            
            Predicate p1, p2;
            
            p1 = builder.equal(root.get("account").get("userType"), "Employee");
            
            Pattern pattern = Pattern.compile("^[NV.0-9]*$");
            Matcher matcher = pattern.matcher(keyword);

            if (matcher.matches())
                p2 = builder.equal(root.get("employeeID"), keyword);
            else
                p2 = builder.like(
                    builder.lower(root.<String>get("employeeName")),
                    "%" + keyword.toLowerCase() + "%"
                );
            
            Predicate andCondition = builder.and(p1, p2);
                    
            query.select(root).where(andCondition);
           
            ArrayList<Employee> listEmployee = (ArrayList<Employee>) session.createQuery(query).getResultList();
            
            if (listEmployee.size() > 0) {
                return listEmployee;
            }
            
            return null;
        }
    }
    
    public ArrayList<Employee> getListEmployee(){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
            Root<Employee> root = query.from(Employee.class);
            
            Predicate p = builder.equal(root.get("account").get("userType"), "Employee");
                    
            query.select(root).where(p);
            
            return (ArrayList<Employee>) session.createQuery(query).getResultList();
        }
    }
    
    public int getEmployeeID(){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
            Root<Employee> root = query.from(Employee.class);
            
            query.select(root);
            
            ArrayList<Employee> listEmployee = (ArrayList<Employee>) session.createQuery(query).getResultList();
            
            if (listEmployee.size() > 0) {
                return listEmployee.size();
            }
           
            return 0;
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
