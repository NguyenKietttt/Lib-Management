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
    
    public Card getCardByID(String cardID){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Card> query = builder.createQuery(Card.class);
            Root<Card> root = query.from(Card.class);
            
            root.fetch("listBorrowReturn", JoinType.INNER);
            
            Predicate p1 = builder.equal(root.get("cardID"), cardID);
            Predicate p2 = builder.equal(root.get("listBorrowReturn").get("returnDate"), null);
            
            Predicate andCondition = builder.and(p1, p2);
            
            query.select(root).where(andCondition);
            
            ArrayList<Card> listCard = (ArrayList<Card>) session.createQuery(query).getResultList();
            
            if (listCard.size() > 0) {
                return listCard.get(0);
            }
           
            return null;
        }
    }
}
