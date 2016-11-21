package com.neu.xunweb.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.neu.xunweb.exception.AccException;
import com.neu.xunweb.pojo.Drug;

public class DrugDAO extends DAO{
	
	public Drug get(int drugid) throws AccException{
		
		try {
			begin();
			Query query = getSession().createQuery("from Drug a where a.drugid =:drugid");
			query.setParameter("drugid", drugid);
			Drug drug = (Drug) query.uniqueResult();
			
			commit();
			return drug;		
		} catch (HibernateException e) {
			rollback();
			throw new AccException("Exception while getting a drug: " + e.getMessage());
		}
	}
	
	public void update(Drug drug, String name, String pharma, String price, String quantity) throws AccException{
		
		try {
			begin();
			drug.setName(name);
			drug.setPharma(pharma);
			drug.setPrice(price);
			drug.setQuantity(quantity);
			getSession().save(drug);
			
			commit();		
		} catch (HibernateException e) {
			rollback();
			throw new AccException("Exception while updating the drug: " + e.getMessage());
		}
	}
	
	public void delete(Drug drug) throws AccException{
		
		try {
			begin();
			drug.setRemoved(1);
			getSession().save(drug);
			
			commit();		
		} catch (HibernateException e) {
			rollback();
			throw new AccException("Exception while deleting a drug: " + e.getMessage());
		}
	}

}
