package com.neu.xunweb.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.neu.xunweb.dao.PharmacistAccountDAO;
import com.neu.xunweb.pojo.PharmacistAccount;

@Controller
public class ApprovePharmacistController {
	
	@Autowired
	@Qualifier("pharmacistaccountdao")
	PharmacistAccountDAO pharmacistaccountdao;
	
	@RequestMapping(value = "/pharmacisttable.htm", method=RequestMethod.POST)
	protected void doManageDoctor(HttpServletRequest req, HttpServletResponse resp){
		
		try{
		
		JSONObject obj = new JSONObject();
		
		List<PharmacistAccount> l = pharmacistaccountdao.getall();
		
		List<PharmacistAccount> list = new ArrayList<PharmacistAccount>();
		
		for(PharmacistAccount pa: l){
			PharmacistAccount newpharmacistaccount = new PharmacistAccount();
			newpharmacistaccount.setAccountid(pa.getAccountid());
			newpharmacistaccount.setName(pa.getName());
			newpharmacistaccount.setTelephone(pa.getTelephone());
			newpharmacistaccount.setStatus(pa.getStatus());
			newpharmacistaccount.setOrganizationname(pa.getOrganization().getName());
			newpharmacistaccount.setOrganizationtype(pa.getOrganization().getType());
			list.add(newpharmacistaccount);
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
        
        List<PharmacistAccount> sublist = null;
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
	
	@RequestMapping(value = "/approvepharmacist.htm", method=RequestMethod.POST)
	protected void doApproveDoctor(HttpServletRequest req, HttpServletResponse resp){

		try{
		
		String accountid = req.getParameter("accountid");
		int newaccountid = Integer.parseInt(accountid);
		
		PharmacistAccount pharmacistaccount = pharmacistaccountdao.get(newaccountid);
		pharmacistaccountdao.approve(pharmacistaccount);
		
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
    }
}
