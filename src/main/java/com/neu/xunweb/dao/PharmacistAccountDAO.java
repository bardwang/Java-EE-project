package com.neu.xunweb.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.neu.xunweb.exception.AccException;
import com.neu.xunweb.pojo.Account;
import com.neu.xunweb.pojo.Organization;
import com.neu.xunweb.pojo.PharmacistAccount;

public class PharmacistAccountDAO extends DAO{
	
	public PharmacistAccount create(Account account, Organization organization, String name, String telephone)
			throws AccException{
        try {
            begin();
            //insert Code here
            PharmacistAccount pharmacistAccount = new PharmacistAccount();
            pharmacistAccount.setUsername(account.getUsername());
            pharmacistAccount.setPassword(account.getPassword());
            pharmacistAccount.setRole(account.getRole());
            pharmacistAccount.setName(name);
            pharmacistAccount.setTelephone(telephone);
            pharmacistAccount.setOrganization(organization);
            
            getSession().save(pharmacistAccount);
            commit();
            return pharmacistAccount;
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new AccException("Exception while creating a pharmacistaccount: " + e.getMessage());
        }
    }
	
	public List<PharmacistAccount> getall() throws AccException{
		try {
			begin();
			Query query = getSession().createQuery("from PharmacistAccount d where d.status = 0");
			List<PharmacistAccount> list = query.list();
			
			commit();
			return list;
		} catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new AccException("Exception while getting all pending pharmacistaccounts: " + e.getMessage());
        }
	}
	
	public PharmacistAccount get(int accountid) throws AccException{
		try {
			begin();
			Query query = getSession().createQuery("from PharmacistAccount d where d.accountid =:accountid");
			query.setParameter("accountid", accountid);
			PharmacistAccount pharmacistaccount = (PharmacistAccount) query.uniqueResult();
			Hibernate.initialize(pharmacistaccount.getOrganization());
			Organization organization = pharmacistaccount.getOrganization();
			if(organization != null){
			Hibernate.initialize(organization.getDrugs());
			}
			
			commit();
			return pharmacistaccount;
		} catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new AccException("Exception while getting a pharmacistaccount: " + e.getMessage());
        }
	}
	
	public PharmacistAccount get(String username) throws AccException{
		
		try {
			begin();
			Query query = getSession().createQuery("from PharmacistAccount a where a.username =:username");
			query.setParameter("username", username);
			PharmacistAccount pharmacistaccount = (PharmacistAccount) query.uniqueResult();
			
			commit();
			return pharmacistaccount;			
		} catch (HibernateException e) {
			rollback();
			throw new AccException("Exception while getting a pharmacistaccount: " + e.getMessage());
		}
	}
	
	public void approve(PharmacistAccount pharmacistaccount) throws AccException{
		
		try {
			begin();
			pharmacistaccount.setStatus(1);
			getSession().save(pharmacistaccount);
			
			commit();		
		} catch (HibernateException e) {
			rollback();
			throw new AccException("Exception while approving a pharmacistaccount: " + e.getMessage());
		}
	}

}
