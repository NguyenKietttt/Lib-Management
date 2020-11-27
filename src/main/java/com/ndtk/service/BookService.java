/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.service;

import com.ndtk.Library_web.HibernateUtil;
import com.ndtk.pojo.Book;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class BookService {
    private static final SessionFactory factory = HibernateUtil.getFACTORY();
    
    public Book getBookByID(int ID){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Book> query = builder.createQuery(Book.class);
            Root<Book> root = query.from(Book.class);
            
            root.fetch("category", JoinType.INNER);
            root.fetch("author", JoinType.INNER);
            root.fetch("publisher", JoinType.INNER);
            root.fetch("listBorrowReturnDetail", JoinType.LEFT);
            
            Predicate p = builder.equal(root.get("bookID"), ID);
            
            query.select(root).where(p);
            
            ArrayList<Book> listBook = (ArrayList<Book>) session.createQuery(query).getResultList();
            
            if (listBook.size() > 0) {
                return listBook.get(0);
            }
           
            return null;
        }
    }
    
    public ArrayList<Book> getAllBooks(String keyword, int categoryID, int authorID, int publisherID){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Book> query = builder.createQuery(Book.class);
            Root<Book> root = query.from(Book.class);
            
            root.fetch("category", JoinType.INNER);
            root.fetch("author", JoinType.INNER);
            root.fetch("publisher", JoinType.INNER);
            
            Predicate p1, p2, p3, p4;
            
            if (categoryID == -1)
                p1 = builder.notEqual(root.get("category").get("categoryID"), categoryID);
            else
                p1 = builder.equal(root.get("category").get("categoryID"), categoryID);
            
            if (authorID == -1)
                p2 = builder.notEqual(root.get("author").get("authorID"), authorID);
            else
                p2 = builder.equal(root.get("author").get("authorID"), authorID);
            
            if (publisherID == -1)
                p3 = builder.notEqual(root.get("publisher").get("publisherID"), publisherID);
            else
                p3 = builder.equal(root.get("publisher").get("publisherID"), publisherID);
            
            if (keyword == null)
                p4 = builder.isNotNull(root.get("bookID"));
            else{
                Pattern pattern = Pattern.compile("^[0-9]*$");
                Matcher matcher = pattern.matcher(keyword);
                
                if (matcher.matches())
                    p4 = builder.equal(root.get("bookID"), Integer.parseInt(keyword));
                else
                    p4 = builder.like(
                        builder.lower(root.<String>get("bookName")),
                        "%" + keyword.toLowerCase() + "%"
                    );
            }
            
            Predicate andCondition = builder.and(p1, p2, p3, p4);
                    
            query.select(root).where(andCondition);
           
            ArrayList<Book> listBook = (ArrayList<Book>) session.createQuery(query).getResultList();
            
            if (listBook.size() > 0) {
                return listBook;
            }
            
            return null;
        }
    }

    public boolean addOrSaveBook(Book book){
        try(Session session = factory.openSession()){
            try{
                session.getTransaction().begin();

                session.saveOrUpdate(book);

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
