package com.neu.xunweb.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.xunweb.dao.DoctorAccountDAO;
import com.neu.xunweb.dao.OrganizationDAO;
import com.neu.xunweb.dao.PatientAccountDAO;
import com.neu.xunweb.dao.TimeDAO;
import com.neu.xunweb.pojo.Appointment;
import com.neu.xunweb.pojo.DoctorAccount;
import com.neu.xunweb.pojo.Organization;
import com.neu.xunweb.pojo.PatientAccount;
import com.neu.xunweb.pojo.Time;
import com.neu.xunweb.springview.PatientPdfView;

@Controller
public class PatientSelectController {
	
	@Autowired
	@Qualifier("doctoraccountdao")
	DoctorAccountDAO doctoraccountdao;
	
	@Autowired
	@Qualifier("patientaccountdao")
	PatientAccountDAO patientaccountdao;
	
	@Autowired
	@Qualifier("organizationdao")
	OrganizationDAO organizationdao;
	
	@Autowired
	@Qualifier("timedao")
	TimeDAO timedao;
	
	@RequestMapping(value = "/patientselectorganization.htm", method=RequestMethod.POST)
	protected String patientSelectOrganization(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
		
		session.setAttribute("city", req.getParameter("city"));
		session.setAttribute("type", req.getParameter("type"));
		session.setAttribute("date", req.getParameter("date"));

        return "PatientSelectOrganization";
    }
	
	@RequestMapping(value = "/timesheet.htm", method=RequestMethod.POST)
	protected String timesheet(HttpServletRequest req, HttpServletResponse resp){

		try{
		
		HttpSession session = req.getSession();
		String accountid = (String) req.getParameter("accountid");
		int newaccountid = Integer.parseInt(accountid);
		session.setAttribute("accountid", accountid);
		
		DoctorAccount da = doctoraccountdao.get(newaccountid);
		
		req.setAttribute("doctoraccount", da);
		
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
		
        return "Timesheet";
    }
	
	@RequestMapping(value = "/updatetimesheet.htm", method=RequestMethod.POST)
	protected ModelAndView updatetimesheet(HttpServletRequest req, HttpServletResponse resp){
		
		HttpSession session = req.getSession();
		int timeid = (Integer) session.getAttribute("timeid");
		String specifictime = (String) req.getParameter("time");
		String organizationname = (String) session.getAttribute("organizationname");
		
		Time time = null;
		try{
		time = timedao.get(timeid);
		timedao.update(time, specifictime);
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
		
		PatientAccount pa = null;
		
		try{
		int patientid = (Integer) session.getAttribute("patientid");
		pa = patientaccountdao.get(patientid);
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
		
		String accountid = (String) session.getAttribute("accountid");
		int id = Integer.parseInt(accountid);
		
		DoctorAccount da = null;
		try{
		da = doctoraccountdao.get(id);
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
		
		Appointment appointment = new Appointment();
		appointment.setDoctoraccount(da);
		appointment.setTime(specifictime);
		appointment.setDate(time.getDate());
		appointment.setPatientname(pa.getName());
		appointment.setReservationnumber(System.currentTimeMillis());
		
		try{
		patientaccountdao.addappointment(pa, appointment);
		doctoraccountdao.addappointment(da, appointment);
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
		
		PatientPdfView pdf = new PatientPdfView();
		pdf.setPatientname(pa.getName());
		pdf.setDoctorname(da.getName());
		pdf.setReservationnumber(appointment.getReservationnumber());
		pdf.setTime(specifictime);
		pdf.setDate(time.getDate());
		pdf.setAddress(organizationname);
		
        return new ModelAndView(pdf);
    }
	
	@RequestMapping(value = "/selecttime.htm", method=RequestMethod.POST)
	protected void selecttime(HttpServletRequest req, HttpServletResponse resp){

		try{
		
		HttpSession session = req.getSession();
		JSONObject obj = new JSONObject();
		String date = (String) session.getAttribute("date");
		String accountid = (String) session.getAttribute("accountid");
		int id = Integer.parseInt(accountid);		
		DoctorAccount da = doctoraccountdao.get(id);
		Set<Time> times = da.getTimes();
		
		Time realtime = null;
		for(Time time: times){
			if(time.getDate().equals(date)){
				realtime = time;
			}
		}
		session.setAttribute("timeid", realtime.getTimeid());
		obj.put("time1", realtime.getNineam());
		obj.put("time2", realtime.getTenam());
		obj.put("time3", realtime.getElevenam());
		obj.put("time4", realtime.getOnepm());
		obj.put("time5", realtime.getTwopm());
		obj.put("time6", realtime.getThreepm());
		obj.put("time7", realtime.getFourpm());
		PrintWriter out = resp.getWriter();
        out.println(obj);
        
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
        
    }
	
	@RequestMapping(value = "/patientselectdoctor.htm", method=RequestMethod.POST)
	protected String patientSelectDoctor(HttpServletRequest req, HttpServletResponse resp){

		HttpSession session = req.getSession();
		session.setAttribute("organizationname", req.getParameter("name"));
		
        return "PatientSelectDoctor";
    }
	
	@RequestMapping(value = "/doctortable.htm", method=RequestMethod.POST)
	protected void doctortable(HttpServletRequest req, HttpServletResponse resp){

		try{
		
		HttpSession session = req.getSession();
		String organizationname = (String) session.getAttribute("organizationname");
		
		JSONObject obj = new JSONObject();
		
		Organization organization = organizationdao.get(organizationname);
		Set<DoctorAccount> set = organization.getDoctoraccounts();
		
		List<DoctorAccount> list = new ArrayList<DoctorAccount>();
		
		for(DoctorAccount da: set){
			if(da.getStatus() == 0){
				continue;
			}
			DoctorAccount newda = new DoctorAccount();
			newda.setAccountid(da.getAccountid());
			newda.setName(da.getName());
			newda.setConcentration(da.getConcentration());
			list.add(newda);
		}
		
		Collections.sort(list, new Comparator<DoctorAccount>() {
	        @Override
	        public int compare(DoctorAccount d1, DoctorAccount d2)
	        {
	            return  d1.getConcentration().compareTo(d2.getConcentration());
	        }
	    });
		
		int totalpage = list.size() / 5;
		if(list.size() % 5 > 0){
			totalpage++;
		}
		
		int pagebegin = 1;
        int pageend = 5;
        int currentpage = 1;
        
        if(req.getParameter("cpage") != null){
            currentpage = Integer.parseInt(req.getParameter("cpage"));
        }
        
        if(totalpage <= 5 && currentpage <= 3){
        	pageend = totalpage;
        }else if(totalpage - currentpage <= 2){
        	pagebegin = totalpage - 4;
        	pageend = totalpage;
        }else if(totalpage  - currentpage > 2){
        	pagebegin = 1;
        	pageend = 5;
        }else{
        	pagebegin = currentpage - 2;
        	pageend = currentpage + 2;
        }
        
        List<DoctorAccount> sublist = null;
        if(currentpage * 5 > list.size()){
        sublist = list.subList((currentpage - 1) * 5, list.size());
        }else{
        sublist = list.subList((currentpage - 1) * 5, currentpage * 5);	
        }
		
		obj.put("list", sublist);
		obj.put("currentpage", currentpage);
		obj.put("pagebegin", pagebegin);
		obj.put("pageend", pageend);
		
		PrintWriter out = resp.getWriter();
        out.println(obj);
        
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
    }

}
