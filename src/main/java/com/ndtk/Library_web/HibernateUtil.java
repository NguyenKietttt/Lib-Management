/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.Library_web;

import com.ndtk.pojo.Account;
import com.ndtk.pojo.Author;
import com.ndtk.pojo.Book;
import com.ndtk.pojo.BorrowReturn;
import com.ndtk.pojo.BorrowReturnDetail;
import com.ndtk.pojo.Card;
import com.ndtk.pojo.Category;
import com.ndtk.pojo.Employee;
import com.ndtk.pojo.Publisher;
import com.ndtk.pojo.Reader;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author ACER
 */
public class HibernateUtil {
    private static final SessionFactory FACTORY;
    
    static{
        Configuration conf = new Configuration();
        Properties pros = new Properties();
        pros.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        pros.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
        pros.put(Environment.URL, "jdbc:mysql://127.0.0.1:3306/Library");
        pros.put(Environment.USER, "admin");
        pros.put(Environment.PASS, "123");
        conf.setProperties(pros);
        
        conf.addAnnotatedClass(Account.class);
        conf.addAnnotatedClass(Category.class);
        conf.addAnnotatedClass(Author.class);
        conf.addAnnotatedClass(Publisher.class);
        conf.addAnnotatedClass(Book.class);
        conf.addAnnotatedClass(Employee.class);
        conf.addAnnotatedClass(Card.class);
        conf.addAnnotatedClass(Reader.class);
        conf.addAnnotatedClass(BorrowReturn.class);
        conf.addAnnotatedClass(BorrowReturnDetail.class);
        
        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(conf.getProperties())
                .build();
        
        FACTORY = conf.buildSessionFactory(registry);
    }

    /**
     * @return the FACTORY
     */
    public static SessionFactory getFACTORY() {
        return FACTORY;
    }
}
