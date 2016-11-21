package com.neu.xunweb.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.neu.xunweb.exception.AccException;
import com.neu.xunweb.pojo.DoctorAccount;
import com.neu.xunweb.pojo.Drug;
import com.neu.xunweb.pojo.Organization;

public class OrganizationDAO extends DAO{
	
	public Organization create(String name, String type, String city)
			throws AccException{
        try {
            begin();
            //insert Code here
            Organization organization = new Organization();
            organization.setName(name);
            organization.setType(type);
            organization.setCity(city);
            getSession().save(organization);
            commit();
            
            return organization;
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new AccException("Exception while creating an organization: " + e.getMessage());
        }
    }
	
	public Organization get(String name) throws AccException{
		
		try {
			begin();
			Query query = getSession().createQuery("from Organization o where o.name =:name");
			query.setParameter("name", name);
			Organization organization = (Organization) query.uniqueResult();
			if(organization != null){
			Hibernate.initialize(organization.getDoctoraccounts());
			}
			commit();
			
			return organization;		
		} catch (HibernateException e) {
			rollback();
			throw new AccException("Exception while getting an organization: " + e.getMessage());
		}
	}
	
	public List<Organization> getall(String city, String type) throws AccException{
		
		try {
			begin();
			Query query = getSession().createQuery("from Organization o where o.city =:city and o.type =:type and o.removed = 0");
			query.setParameter("city", city);
			query.setParameter("type", type);
			List<Organization> list = query.list();
			
			for(Organization organization: list){
				Hibernate.initialize(organization.getDoctoraccounts());
				for(DoctorAccount da: organization.getDoctoraccounts()){
					Hibernate.initialize(da.getTimes());
				}
			}
			
			commit();
			
			return list;			
		} catch (HibernateException e) {
			rollback();
			throw new AccException("Exception while get all the organizations: " + e.getMessage());
		}
	}
	
	public void delete(Organization organization) throws AccException{
		
		try {
			begin();
			organization.setRemoved(1);
			getSession().save(organization);
			
			commit();			
		} catch (HibernateException e) {
			rollback();
			throw new AccException("Exception while deleting an organization: " + e.getMessage());
		}
	}
	
	public void addDrug(Organization organization, Drug drug) throws AccException{
		
		try {
			begin();
			Set<Drug> drugs = organization.getDrugs();
			drugs.add(drug);
			drug.setOrganization(organization);
			getSession().save(organization);
			
			commit();			
		} catch (HibernateException e) {
			rollback();
			throw new AccException("Exception while adding a drug: " + e.getMessage());
		}
	}	

}
