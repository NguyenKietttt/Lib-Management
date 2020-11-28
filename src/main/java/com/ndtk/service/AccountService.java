/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndtk.service;

import com.ndtk.pojo.Account;
import com.ndtk.Library_web.HibernateUtil;
import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author ACER
 */
public class AccountService {
    private static final SessionFactory factory = HibernateUtil.getFACTORY();
    
    public Account getAccountByID(String accountID){
        try(Session session = factory.openSession()){
            return session.get(Account.class, accountID);
        }
    }
    
    public int loginAccount(String accountID, String Password){
        try(Session session = factory.openSession()){
            try{
                EntityManager em = factory.createEntityManager();

                StoredProcedureQuery query = em.createNamedStoredProcedureQuery("spLogin");
                query.setParameter("AccountID", accountID);
                query.setParameter("Password", Password);
                query.execute();
                
                int count = (int) query.getOutputParameterValue("OutValue");
                
                em.close();
                
                return count;
            }
            catch(Exception ex){
                session.getTransaction().rollback();
                return 0;
            }
        }
    }
    
    public boolean addOrSaveAccount(Account acc){
        try(Session session = factory.openSession()){
            try{
                EntityManager em = factory.createEntityManager();
                em.getTransaction().begin();
                
                StoredProcedureQuery query = em.createNamedStoredProcedureQuery("spAddAccount");
                query.setParameter("AccountID", acc.getAccountID());
                query.setParameter("Password", acc.getPasswordHash());
                query.setParameter("Salt", acc.getSalt());
                query.setParameter("EmployeeID", acc.getEmployee().getEmployeeID());
                
                query.execute();
                
                em.getTransaction().commit();
                em.close();
            }
            catch(Exception ex){
                session.getTransaction().rollback();
                return false;
            }
        }
        
        return true;
    }
}
