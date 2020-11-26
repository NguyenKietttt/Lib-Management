/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.service;

import com.ndtk.Library_web.HibernateUtil;
import com.ndtk.pojo.Author;
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
public class AuthorService {
    private static final SessionFactory factory = HibernateUtil.getFACTORY();
    
    public ArrayList<Author> getAllAuthor(){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Author> query = builder.createQuery(Author.class);
            Root<Author> root = query.from(Author.class);
                    
            query.orderBy(builder.asc(root.get("authorName"))).select(root);
           
            ArrayList<Author> listAuthor = (ArrayList<Author>) session.createQuery(query).getResultList();
            
            if (listAuthor.size() > 0) {
                return listAuthor;
            }
            
            return null;
        }
    }
    
    public Author getAuthorByName(String name){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Author> query = builder.createQuery(Author.class);
            Root<Author> root = query.from(Author.class);
            
            Predicate p = builder.equal(root.get("authorName"), name);
                    
            query.select(root).where(p);
            
            ArrayList<Author> listAuthor = (ArrayList<Author>) session.createQuery(query).getResultList();
            
            if (listAuthor.size() > 0) {
                return listAuthor.get(0);
            }
            
            return null;
        }
    }
    
    public Author getAuthorByID(int id){
        try(Session session = factory.openSession()){
            return session.get(Author.class, id);
        }
    }

    public boolean addOrSaveAuthor(Author author){
        try(Session session = factory.openSession()){
            try{
                session.getTransaction().begin();

                session.saveOrUpdate(author);

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
