package com.neu.xunweb.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.neu.xunweb.dao.OrganizationDAO;
import com.neu.xunweb.pojo.DoctorAccount;
import com.neu.xunweb.pojo.Organization;
import com.neu.xunweb.pojo.Time;

@Controller
public class ManageOrganizationController {
	
	@Autowired
	@Qualifier("organizationdao")
	OrganizationDAO organizationdao;

	@RequestMapping(value = "/manageorganization.htm", method=RequestMethod.POST)
	protected String doConnectOrganization(@RequestParam("city")String city, @RequestParam("type")String type, HttpServletRequest req, HttpServletResponse resp){
		
		HttpSession session = req.getSession();
		session.setAttribute("city", city);
		session.setAttribute("type", type);
		
        return "ManageOrganization";
    }
	
	@RequestMapping(value = "/organizationtable.htm", method=RequestMethod.POST)
	protected void doManageOrganization(HttpServletRequest req, HttpServletResponse resp, HttpSession session){

		try{
		String city = (String) session.getAttribute("city");
		String type = (String) session.getAttribute("type");
		String date = (String) session.getAttribute("date");
		
		JSONObject obj = new JSONObject();
		
		List<Organization> l = organizationdao.getall(city, type);
		
		List<Organization> list = new ArrayList<Organization>();
		
		for(Organization organization: l){
			Organization neworganization = new Organization();
			neworganization.setName(organization.getName());
			int available = 0;
			int totalnum = 0;
			for(DoctorAccount da: organization.getDoctoraccounts()){
				if(da.getStatus() == 0){
					continue;
				}
				int avail = 7;
				int total = 7;
				for(Time time: da.getTimes()){
					if(time.getDate().equals(date)){
						avail = avail - (time.getNineam() + time.getTenam() + time.getElevenam() + 
							time.getOnepm()+ time.getTwopm() + time.getThreepm() + time.getFourpm());
					}
				}
				available = available + avail;
				totalnum = totalnum + total;
			}
			neworganization.setAvailable(available);
			neworganization.setTotal(totalnum);
			list.add(neworganization);
		}
		
		
		Collections.sort(list, new Comparator<Organization>() {
	        @Override
	        public int compare(Organization o1, Organization o2)
	        {
	            return  o1.getName().compareTo(o2.getName());
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
        
        List<Organization> sublist = null;
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
	
	@RequestMapping(value = "/organizationadd.htm", method=RequestMethod.POST)
	protected void doAddOrganization(@ModelAttribute("organization")Organization organization, HttpServletRequest req, HttpServletResponse resp){

		try{
			
		JSONObject obj = new JSONObject();
		String organizationname = organization.getName();
		Organization organ = organizationdao.get(organizationname);
		if(organ != null){
			obj.put("error", "This name was already existed");
			PrintWriter out = resp.getWriter();
	        out.println(obj);
	        return;
		}
		
		if(organizationname.equals("")){
			obj.put("error", "Please input an organization name");
			PrintWriter out = resp.getWriter();
	        out.println(obj);
	        return;
		}
		
		organizationdao.create(organization.getName(), organization.getType(), organization.getCity());
		
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
    }
	
	@RequestMapping(value = "/organizationdelete.htm", method=RequestMethod.POST)
	protected void doDeleteOrganization(HttpServletRequest req, HttpServletResponse resp){

		try{
			
		String name = req.getParameter("name");
		Organization organization = organizationdao.get(name);	
		organizationdao.delete(organization);
		
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
    }
}
