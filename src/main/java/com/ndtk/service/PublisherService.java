/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.service;

import com.ndtk.Library_web.HibernateUtil;
import com.ndtk.pojo.Publisher;
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
public class PublisherService {
    private static final SessionFactory factory = HibernateUtil.getFACTORY();
    
    public ArrayList<Publisher> getAllPublisher(){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Publisher> query = builder.createQuery(Publisher.class);
            Root<Publisher> root = query.from(Publisher.class);
                    
            query.orderBy(builder.asc(root.get("publisherName"))).select(root);
           
            ArrayList<Publisher> listPublisher = (ArrayList<Publisher>) session.createQuery(query).getResultList();
            
            if (listPublisher.size() > 0) {
                return listPublisher;
            }
            
            return null;
        }
    }
    
    public Publisher getPublisherByID(int id){
        try(Session session = factory.openSession()){
            return session.get(Publisher.class, id);
        }
    }

    public Publisher getPublisherByName(String name){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Publisher> query = builder.createQuery(Publisher.class);
            Root<Publisher> root = query.from(Publisher.class);
            
            Predicate p = builder.equal(root.get("publisherName"), name);
                    
            query.select(root).where(p);
            
            ArrayList<Publisher> listPublisher = (ArrayList<Publisher>) session.createQuery(query).getResultList();
            
            if (listPublisher.size() > 0) {
                return listPublisher.get(0);
            }
            
            return null;
        }
    }
        
    public boolean addOrSavePubliser(Publisher publisher){
        try(Session session = factory.openSession()){
            try{
                session.getTransaction().begin();

                session.saveOrUpdate(publisher);

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
