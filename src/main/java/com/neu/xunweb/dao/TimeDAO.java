package com.neu.xunweb.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.neu.xunweb.exception.AccException;
import com.neu.xunweb.pojo.DoctorAccount;
import com.neu.xunweb.pojo.Time;

public class TimeDAO extends DAO{
	
	public Time create(String date, DoctorAccount doctoraccount)
			throws AccException{
        try {
            begin();
            //insert Code here
            Time time = new Time();  
            time.setDate(date);
            time.setDoctoraccount(doctoraccount);
            getSession().save(time);
            
            commit();
            return time;
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new AccException("Exception while creating a time: " + e.getMessage());
        }
    }
	
	public Time get(int timeid)
			throws AccException{
        try {
            begin();
            //insert Code here
            Query query = getSession().createQuery("from Time t where t.timeid =:timeid");
			query.setParameter("timeid", timeid);
			Time time = (Time) query.uniqueResult();
			
            commit();
            return time;
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new AccException("Exception while getting a time: " + e.getMessage());
        }
    }
	
	public void update(Time time, String specfictime) throws AccException{
		
		try {
			begin();
			if(specfictime.equals("9")){
				time.setNineam(1);
			}else if(specfictime.equals("10")){
				time.setTenam(1);
			}else if(specfictime.equals("11")){
				time.setElevenam(1);
			}else if(specfictime.equals("13")){
				time.setOnepm(1);
			}else if(specfictime.equals("14")){
				time.setTwopm(1);
			}else if(specfictime.equals("15")){
				time.setThreepm(1);
			}else if(specfictime.equals("16")){
				time.setFourpm(1);
			}
			getSession().save(time);
			
			commit();			
		} catch (HibernateException e) {
			rollback();
			throw new AccException("Exception while updating the time: " + e.getMessage());
		}
	}
	
	public Time delete(Time time)
			throws AccException{
        try {
            begin();
            //insert Code here
            time.setStatus(0);
            getSession().save(time);
            
            commit();
            return time;
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create user " + username, e);
            throw new AccException("Exception while deleting the time: " + e.getMessage());
        }
    }
}
