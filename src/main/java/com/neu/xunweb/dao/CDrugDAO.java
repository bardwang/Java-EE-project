package com.neu.xunweb.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.neu.xunweb.exception.AccException;
import com.neu.xunweb.pojo.CDrug;

public class CDrugDAO extends DAO{

	public CDrug get(int drugid) throws AccException{
		
		try {
			begin();
			Query query = getSession().createQuery("from CDrug a where a.drugid =:drugid");
			query.setParameter("drugid", drugid);
			CDrug cdrug = (CDrug) query.uniqueResult();
			
			commit();
			return cdrug;
			
		} catch (HibernateException e) {
			rollback();
			throw new AccException("Exception while getting a cdrug: " + e.getMessage());
		}
	}
	
	public CDrug updatequantity(CDrug cdrug, String quantity) throws AccException{
		
		try {
			begin();
			cdrug.setQuantity(quantity);
			getSession().save(cdrug);
			commit();
			return cdrug;
			
		} catch (HibernateException e) {
			rollback();
			throw new AccException("Exception while updating the quantity of the cdrug: " + e.getMessage());
		}
	}
	
	public void delete(CDrug cdrug) throws AccException{
		
		try {
			begin();
			cdrug.setRemoved(1);
			getSession().save(cdrug);
			commit();
			
		} catch (HibernateException e) {
			rollback();
			throw new AccException("Exception while deleting the cdrug: " + e.getMessage());
		}
	}
	
}
