package com.neu.xunweb.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.neu.xunweb.exception.AccException;
import com.neu.xunweb.pojo.Account;
import com.neu.xunweb.pojo.Appointment;
import com.neu.xunweb.pojo.PatientAccount;

public class PatientAccountDAO extends DAO{
	
	public PatientAccount create(Account account, String name, String address, String phone, String email)
			throws AccException{
        try {
            begin();
            //insert Code here
            PatientAccount patientaccount = new PatientAccount();
            patientaccount.setUsername(account.getUsername());
            patientaccount.setPassword(account.getPassword());
            patientaccount.setRole(account.getRole());
            patientaccount.setName(name);
            patientaccount.setAddress(address);
            patientaccount.setPhone(phone);
            patientaccount.setEmail(email);
            getSession().save(patientaccount);
            
            commit();
            return patientaccount;
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new AccException("Exception while creating a patientaccount: " + e.getMessage());
        }
    }
	
	public PatientAccount get(int accountid) throws AccException{
		try {
			begin();
			Query query = getSession().createQuery("from PatientAccount p where p.accountid =:accountid");
			query.setParameter("accountid", accountid);
			PatientAccount patientaccount = (PatientAccount) query.uniqueResult();
			
			commit();
			return patientaccount;
		} catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new AccException("Exception while getting a patientaccount: " + e.getMessage());
        }
	}
	
	public PatientAccount addappointment(PatientAccount patientaccount, Appointment appointment) throws AccException{
		try {
            begin();
            //insert Code here
            patientaccount.setAppointment(appointment);
            appointment.setPatientaccount(patientaccount);
            getSession().save(patientaccount);
            
            commit();
            return patientaccount;
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new AccException("Exception while adding an appointment to the patientaccount: " + e.getMessage());
        }
	}
	
	public void removeappointment(PatientAccount pa, Appointment app) throws AccException{
		try {
            begin();
            //insert Code here
            pa.setAppointment(null);
            app.setPatientaccount(null);        
            app.setDoctoraccount(null);
            getSession().save(pa);
            getSession().save(app);
            commit();
            
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new AccException("Exception while removing an appointment: " + e.getMessage());
        }
	}

}
