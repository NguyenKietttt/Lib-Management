/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.service;

import com.ndtk.Library_web.HibernateUtil;
import com.ndtk.pojo.BorrowReturn;
import com.ndtk.pojo.BorrowReturnDetail;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
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
    
    public List<BorrowReturn> getListBorrowByMonthAndYear(int year, Integer[] quarter){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<BorrowReturn> query = builder.createQuery(BorrowReturn.class);
            
            Root<BorrowReturn> rootBR = query.from(BorrowReturn.class);
            
            query.select(rootBR);
            
            Predicate y = builder.equal(builder.function("YEAR", Integer.class, rootBR.get("borrowDate")), year);
            Predicate q1 = builder.equal(builder.function("MONTH", Integer.class, rootBR.get("borrowDate")), quarter[0]);
            Predicate q2 = builder.equal(builder.function("MONTH", Integer.class, rootBR.get("borrowDate")), quarter[1]);
            Predicate q3 = builder.equal(builder.function("MONTH", Integer.class, rootBR.get("borrowDate")), quarter[2]);
            
            Predicate or = builder.or(q1, q2, q3);
            Predicate and = builder.and(y, or);
            
            query.where(and);
            
            return session.createQuery(query).getResultList();
        }
    }
    
    public List<Object[]> getFinesByMonthAndYear(int year, Integer[] quarter){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
            
            Root<BorrowReturn> rootBR = query.from(BorrowReturn.class);
            
            query.multiselect(rootBR.get("borrowDate"), rootBR.get("fines"));
            query.groupBy(rootBR.get("borrowDate"), rootBR.get("fines"));
            
            Predicate y = builder.equal(builder.function("YEAR", Integer.class, rootBR.get("borrowDate")), year);
            Predicate q1 = builder.equal(builder.function("MONTH", Integer.class, rootBR.get("borrowDate")), quarter[0]);
            Predicate q2 = builder.equal(builder.function("MONTH", Integer.class, rootBR.get("borrowDate")), quarter[1]);
            Predicate q3 = builder.equal(builder.function("MONTH", Integer.class, rootBR.get("borrowDate")), quarter[2]);
            
            Predicate or = builder.or(q1, q2, q3);
            Predicate and = builder.and(y, or);
            
            query.where(and);
            
            return session.createQuery(query).getResultList();
        }
    }
    
    public List<BorrowReturnDetail> getBookBorrowByMonthAndYear(int year, Integer[] quarter){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<BorrowReturnDetail> query = builder.createQuery(BorrowReturnDetail.class);
            
            Root<BorrowReturnDetail> rootBRD = query.from(BorrowReturnDetail.class);
            rootBRD.fetch("borrowReturn", JoinType.INNER);
            
            query.select(rootBRD);
            
            Predicate y = builder.equal(builder.function("YEAR", Integer.class, rootBRD.get("borrowReturn").get("borrowDate")), year);
            Predicate q1 = builder.equal(builder.function("MONTH", Integer.class, rootBRD.get("borrowReturn").get("borrowDate")), quarter[0]);
            Predicate q2 = builder.equal(builder.function("MONTH", Integer.class, rootBRD.get("borrowReturn").get("borrowDate")), quarter[1]);
            Predicate q3 = builder.equal(builder.function("MONTH", Integer.class, rootBRD.get("borrowReturn").get("borrowDate")), quarter[2]);
            
            Predicate or = builder.or(q1, q2, q3);
            Predicate and = builder.and(y, or);
            
            query.where(and);
            
            return session.createQuery(query).getResultList();
        }
    }
    
    public ArrayList<Integer> getListYearBorrowReturn(){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
            Root<BorrowReturn> root = query.from(BorrowReturn.class);
                    
            query.select(builder.function("YEAR", Integer.class, root.get("borrowDate"))).distinct(true);
            
            return (ArrayList<Integer>) session.createQuery(query).getResultList();
        }
    }
    
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
