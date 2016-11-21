package com.neu.xunweb.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.neu.xunweb.exception.AccException;
import com.neu.xunweb.pojo.Account;


public class AccountDAO extends DAO{
	
	public Account create(String username, String password, String role)
			throws AccException{
        try {
            begin();
            //insert Code here
            Account account = new Account();  
            account.setUsername(username);
            account.setPassword(password);
            account.setRole(role);
            getSession().save(account);
            
            commit();
            return account;
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new AccException("Exception while creating a user: " + e.getMessage());
        }
    }
	
	public Account get(String username, String password) throws AccException{
		
		try {
			begin();
			Query query = getSession().createQuery("from Account a where a.username =:username and a.password =:password");
			query.setParameter("username", username);
			query.setParameter("password", password);
			Account account = (Account) query.uniqueResult();
			
			commit();
			return account;
			
		} catch (HibernateException e) {
			rollback();
			throw new AccException("Exception while getting an account: " + e.getMessage());
		}
	}
	
	public Account get(String username) throws AccException{
		
		try {
			begin();
			Query query = getSession().createQuery("from Account a where a.username =:username");
			query.setParameter("username", username);
			Account account = (Account) query.uniqueResult();
			
			commit();
			return account;
			
		} catch (HibernateException e) {
			rollback();
			throw new AccException("Exception while getting an account: " + e.getMessage());
		}
	}

}
