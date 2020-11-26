/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.service;

import com.ndtk.Library_web.HibernateUtil;
import com.ndtk.pojo.Category;
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
public class CategoryService {
    private static final SessionFactory factory = HibernateUtil.getFACTORY();
    
    public ArrayList<Category> getAllCategory(){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Category> query = builder.createQuery(Category.class);
            Root<Category> root = query.from(Category.class);
                    
            query.orderBy(builder.asc(root.get("categoryName"))).select(root);
           
            ArrayList<Category> listCategory = (ArrayList<Category>) session.createQuery(query).getResultList();
            
            if (listCategory.size() > 0) {
                return listCategory;
            }
            
            return null;
        }
    }
    
    public Category getCategoryByID(int id){
        try(Session session = factory.openSession()){
            return session.get(Category.class, id);
        }
    }
    
    public Category getCategoryByName(String name){
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Category> query = builder.createQuery(Category.class);
            Root<Category> root = query.from(Category.class);
            
            Predicate p = builder.equal(root.get("categoryName"), name);
                    
            query.select(root).where(p);
            
            return session.createQuery(query).getSingleResult();
        }
    }
    
    public boolean addOrSaveCategory(Category category){
        try(Session session = factory.openSession()){
            try{
                session.getTransaction().begin();

                session.saveOrUpdate(category);

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
