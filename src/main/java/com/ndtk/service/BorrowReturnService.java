/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.service;

import com.ndtk.Library_web.HibernateUtil;
import com.ndtk.pojo.BorrowReturn;
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

public class BorrowReturnService {
    private static final SessionFactory factory = HibernateUtil.getFACTORY();
    
    public BorrowReturn getBorrowNotReturnByID(String ID){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<BorrowReturn> query = builder.createQuery(BorrowReturn.class);
            Root<BorrowReturn> root = query.from(BorrowReturn.class);

            Predicate p1 = builder.equal(root.get("borrowReturnID"), ID);
            Predicate p2 = builder.isNull(root.get("returnDate"));
            
            Predicate andCondition = builder.and(p1, p2);
            
            query.select(root).where(andCondition);
            
            ArrayList<BorrowReturn> listBR = (ArrayList<BorrowReturn>) session.createQuery(query).getResultList();
            
            if (listBR.size() > 0) {
                return listBR.get(0);
            }
           
            return null;
        }
    }
    
    public BorrowReturn getBorrowReturnByID(String ID){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<BorrowReturn> query = builder.createQuery(BorrowReturn.class);
            Root<BorrowReturn> root = query.from(BorrowReturn.class);

            Predicate p = builder.equal(root.get("borrowReturnID"), ID);
            
            query.select(root).where(p);
            
            ArrayList<BorrowReturn> listBR = (ArrayList<BorrowReturn>) session.createQuery(query).getResultList();
            
            if (listBR.size() > 0) {
                return listBR.get(0);
            }
           
            return null;
        }
    }
    
    public int getBorrowReturnID(){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<BorrowReturn> query = builder.createQuery(BorrowReturn.class);
            Root<BorrowReturn> root = query.from(BorrowReturn.class);
            
            query.select(root);
            
            ArrayList<BorrowReturn> listBR = (ArrayList<BorrowReturn>) session.createQuery(query).getResultList();
            
            if (listBR.size() > 0) {
                return listBR.size();
            }
           
            return 0;
        }
    }
    
    public boolean addOrSaveBorrowReturn(BorrowReturn br){
        try(Session session = factory.openSession()){
            try{
                session.getTransaction().begin();

                session.saveOrUpdate(br);

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
