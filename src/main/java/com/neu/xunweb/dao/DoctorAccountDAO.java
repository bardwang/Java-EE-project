package com.neu.xunweb.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.neu.xunweb.exception.AccException;
import com.neu.xunweb.pojo.Account;
import com.neu.xunweb.pojo.Appointment;
import com.neu.xunweb.pojo.CDrug;
import com.neu.xunweb.pojo.DoctorAccount;
import com.neu.xunweb.pojo.Organization;

public class DoctorAccountDAO extends DAO{

	public DoctorAccount create(Account account, Organization organization,String name, String telephone, String concentration, String yearsofexperience, String background)
			throws AccException{
        try {
            begin();
            //insert Code here
            DoctorAccount doctoraccount = new DoctorAccount();
            doctoraccount.setUsername(account.getUsername());
            doctoraccount.setPassword(account.getPassword());
            doctoraccount.setRole(account.getRole());
            doctoraccount.setName(name);
            doctoraccount.setTelephone(telephone);
            doctoraccount.setConcentration(concentration);
            doctoraccount.setYearsofexperience(yearsofexperience);
            doctoraccount.setBackground(background);
            doctoraccount.setOrganization(organization);
            getSession().save(doctoraccount);
            
            commit();
            return doctoraccount;
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new AccException("Exception while creating a doctor account: " + e.getMessage());
        }
    }
	
	public List<DoctorAccount> getallpending() throws AccException{
		try {
			begin();
			Query query = getSession().createQuery("from DoctorAccount d where d.status = 0");
			List<DoctorAccount> list = query.list();
			
			commit();
			return list;
		} catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new AccException("Exception while getting all pending doctoraccounts: " + e.getMessage());
        }
	}
	
	public List<DoctorAccount> getall() throws AccException{
		try {
			begin();
			Query query = getSession().createQuery("from DoctorAccount d where d.status = 1");
			List<DoctorAccount> list = query.list();
			
			commit();
			return list;
		} catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new AccException("Exception while getting all doctoraccounts: " + e.getMessage());
        }
	}
	
	public DoctorAccount get(int accountid) throws AccException{
		try {
			begin();
			Query query = getSession().createQuery("from DoctorAccount d where d.accountid =:accountid");
			query.setParameter("accountid", accountid);
			DoctorAccount doctoraccount = (DoctorAccount) query.uniqueResult();
			Hibernate.initialize(doctoraccount.getAppointments());
			Hibernate.initialize(doctoraccount.getCdrugs());
			Hibernate.initialize(doctoraccount.getOrganization());
			Hibernate.initialize(doctoraccount.getTimes());
			
			Organization organization = doctoraccount.getOrganization();
			if(organization != null){
			Hibernate.initialize(organization.getDrugs());
			}
			
			commit();
			return doctoraccount;
		} catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new AccException("Exception while getting a doctoraccount: " + e.getMessage());
        }
	}
	
	public void approve(DoctorAccount doctoraccount) throws AccException{
		
		try {
			begin();
			doctoraccount.setStatus(1);
			getSession().save(doctoraccount);
			
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new AccException("Exception while approving a doctoraccount: " + e.getMessage());
		}
	}
	
	public void addappointment(DoctorAccount doctoraccount, Appointment appointment) throws AccException{
		try {
            begin();
            //insert Code here
            Set<Appointment> sets = doctoraccount.getAppointments();
            sets.add(appointment);
            appointment.setDoctoraccount(doctoraccount);
            getSession().save(doctoraccount);
            
            commit();
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new AccException("Exception while adding an appointment: " + e.getMessage());
        }
	}
	
	public void adddrug(DoctorAccount doctoraccount, CDrug cdrug) throws AccException{
		try {
            begin();
            //insert Code here
            Set<CDrug> sets = doctoraccount.getCdrugs();
            sets.add(cdrug);
            cdrug.setDoctoraccount(doctoraccount);
            getSession().save(doctoraccount);
            
            commit();
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new AccException("Exception while adding a cdrug: " + e.getMessage());
        }
	}
	
	public void removedrug(DoctorAccount doctoraccount, CDrug drug) throws AccException{
		try {
            begin();
            //insert Code here
            Set<CDrug> sets = doctoraccount.getCdrugs();
            sets.remove(drug);
            getSession().save(doctoraccount);
            
            commit();
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new AccException("Exception while deleting a cdrug: " + e.getMessage());
        }
	}
	
	public void emptydrugs(DoctorAccount doctoraccount) throws AccException{
		try {
            begin();
            //insert Code here
            Set<CDrug> set = doctoraccount.getCdrugs();
            for(CDrug cdrug: set){
            	cdrug.setRemoved(1);
            }
            getSession().save(doctoraccount);
            
            commit();
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new AccException("Exception while clearing cdrugs: " + e.getMessage());
        }
	}
}
