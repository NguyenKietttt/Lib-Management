/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.service;

import com.ndtk.Library_web.HibernateUtil;
import com.ndtk.pojo.Card;
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
public class ReaderService {
    private static final SessionFactory factory = HibernateUtil.getFACTORY();
    
    public ArrayList<Reader> filterReader(String keyword){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Reader> query = builder.createQuery(Reader.class);
            Root<Reader> root = query.from(Reader.class);
            
            Predicate p;
            
            Pattern pattern = Pattern.compile("^[C.0-9]*$");
            Matcher matcher = pattern.matcher(keyword);

            if (matcher.matches())
                p = builder.equal(root.get("card").get("cardID"), keyword);
            else
                p = builder.like(
                    builder.lower(root.<String>get("readerName")),
                    "%" + keyword.toLowerCase() + "%"
                );
                    
            query.select(root).where(p);
           
            ArrayList<Reader> listReader = (ArrayList<Reader>) session.createQuery(query).getResultList();
            
            if (listReader.size() > 0) {
                return listReader;
            }
            
            return null;
        }
    }
    
    public ArrayList<Reader> getListReader(){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Reader> query = builder.createQuery(Reader.class);
            Root<Reader> root = query.from(Reader.class);
                    
            query.select(root);
            
            return (ArrayList<Reader>) session.createQuery(query).getResultList();
        }
    }
    
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
