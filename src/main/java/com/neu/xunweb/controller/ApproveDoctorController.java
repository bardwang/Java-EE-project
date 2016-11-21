package com.neu.xunweb.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neu.xunweb.dao.DoctorAccountDAO;
import com.neu.xunweb.dao.OrganizationDAO;
import com.neu.xunweb.dao.TimeDAO;
import com.neu.xunweb.pojo.DoctorAccount;

@Controller
public class ApproveDoctorController {
	
	@Autowired
	@Qualifier("doctoraccountdao")
	DoctorAccountDAO doctoraccountdao;
	
	@Autowired
	@Qualifier("organizationdao")
	OrganizationDAO organizationdao;
	
	@RequestMapping(value = "/pengingdoctortable.htm", method=RequestMethod.POST)
	protected void doManageDoctor(HttpServletRequest req, HttpServletResponse resp){
		
		try{
		
		JSONObject obj = new JSONObject();
		
		List<DoctorAccount> l = doctoraccountdao.getallpending();		
		List<DoctorAccount> list = new ArrayList<DoctorAccount>();
		
		for(DoctorAccount doctoraccount: l){
			DoctorAccount newdoctoraccount = new DoctorAccount();
			newdoctoraccount.setAccountid(doctoraccount.getAccountid());
			newdoctoraccount.setName(doctoraccount.getName());
			newdoctoraccount.setConcentration(doctoraccount.getConcentration());
			newdoctoraccount.setTelephone(doctoraccount.getTelephone());
			newdoctoraccount.setStatus(doctoraccount.getStatus());
			newdoctoraccount.setOrganizationname(doctoraccount.getOrganization().getName());
			newdoctoraccount.setOrganizationtype(doctoraccount.getOrganization().getType());
			list.add(newdoctoraccount);
		}
		
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
	
	@RequestMapping(value = "/approvedoctor.htm", method=RequestMethod.POST)
	protected void doApproveDoctor(HttpServletRequest req, HttpServletResponse resp){

		try{
		
		String accountid = req.getParameter("accountid");
		int newaccountid = Integer.parseInt(accountid);
		
		DoctorAccount doctoraccount = doctoraccountdao.get(newaccountid);
		doctoraccountdao.approve(doctoraccount);
		
		TimeDAO timedao = new TimeDAO();
		Date firstdate = new Date();
		Date seconddate = DateUtil.addDays(firstdate, 1);
		Date thirddate = DateUtil.addDays(firstdate, 2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String firstday = sdf.format(firstdate);
		String secondday = sdf.format(seconddate);
		String thirdday = sdf.format(thirddate);
		timedao.create(firstday, doctoraccount);
		timedao.create(secondday, doctoraccount);
		timedao.create(thirdday, doctoraccount);
		
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 

    }

}
