/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.service;

import com.ndtk.Library_web.HibernateUtil;
import com.ndtk.pojo.Card;
import java.util.ArrayList;
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

public class CardService {
    private static final SessionFactory factory = HibernateUtil.getFACTORY();
    
    public int getCardID(){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Card> query = builder.createQuery(Card.class);
            Root<Card> root = query.from(Card.class);
            
            query.select(root);
            
            ArrayList<Card> listBR = (ArrayList<Card>) session.createQuery(query).getResultList();
            
            if (listBR.size() > 0) {
                return listBR.size();
            }
           
            return 0;
        }
    }
    
    
    public Card getCardByID(String cardID){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Card> query = builder.createQuery(Card.class);
            
            Root<Card> root = query.from(Card.class);
            root.fetch("listBorrowReturn", JoinType.LEFT);
            
            Predicate p1 = builder.equal(root.get("cardID"), cardID);
            
            query.select(root).where(p1);
            
            ArrayList<Card> listCard = (ArrayList<Card>) session.createQuery(query).getResultList();
            
            if (listCard.size() > 0) {
                return listCard.get(0);
            }
           
            return null;
        }
    }
    
        public boolean addOrSaveCard(Card c){
        try(Session session = factory.openSession()){
            try{
                session.getTransaction().begin();

                session.saveOrUpdate(c);

                session.getTransaction().commit();
            }
            catch(Exception ex){
                session.getTransaction().rollback();
                return false;
            }
        }

        return true;
    }
        
    public boolean deleteCard(Card c){
        try(Session session = factory.openSession()){
            try{
                session.getTransaction().begin();

                session.delete(c);

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
