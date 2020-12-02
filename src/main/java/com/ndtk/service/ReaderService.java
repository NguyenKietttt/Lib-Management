/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.service;

import com.ndtk.Library_web.HibernateUtil;
import com.ndtk.pojo.Reader;
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
public class ReaderService {
    private static final SessionFactory factory = HibernateUtil.getFACTORY();
    
    public Reader getReaderByCardID(String cardID){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Reader> query = builder.createQuery(Reader.class);
            Root<Reader> root = query.from(Reader.class);
            
            Predicate p = builder.equal(root.get("card").get("cardID"), cardID);
                    
            query.select(root).where(p);
            
            ArrayList<Reader> listReader = (ArrayList<Reader>) session.createQuery(query).getResultList();
            
            if (listReader.size() > 0) {
                return listReader.get(0);
            }
            
            return null;
        }
    }
}
