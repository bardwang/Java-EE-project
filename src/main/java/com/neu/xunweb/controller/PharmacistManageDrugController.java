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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.neu.xunweb.dao.DrugDAO;
import com.neu.xunweb.dao.OrganizationDAO;
import com.neu.xunweb.dao.PharmacistAccountDAO;
import com.neu.xunweb.pojo.Drug;
import com.neu.xunweb.pojo.Organization;
import com.neu.xunweb.pojo.PharmacistAccount;

@Controller
public class PharmacistManageDrugController {
	
	@Autowired
	@Qualifier("pharmacistaccountdao")
	PharmacistAccountDAO pharmacistaccountdao;
	
	@Autowired
	@Qualifier("organizationdao")
	OrganizationDAO organizationdao;
	
	@RequestMapping(value = "/drugtable.htm", method=RequestMethod.POST)
	protected void doManageDrug(HttpServletRequest req, HttpServletResponse resp){
        
		try{
		
		HttpSession session = req.getSession();
		int accountid = (Integer) session.getAttribute("accountid");
		
		PharmacistAccount pharmacistaccount = pharmacistaccountdao.get(accountid);
		
		Organization organization = pharmacistaccount.getOrganization();
		String organizationname = organization.getName();
		
		Set<Drug> set = organization.getDrugs();
		List<Drug> list = new ArrayList<Drug>();
		
		for(Drug drug: set){
			if(drug.getRemoved() == 1){
				continue;
			}
			Drug newdrug = new Drug();
			newdrug.setDrugid(drug.getDrugid());
			newdrug.setName(drug.getName());
			newdrug.setPharma(drug.getPharma());
			newdrug.setPrice(drug.getPrice());
			newdrug.setQuantity(drug.getQuantity());
			list.add(newdrug);
		}
		
		Collections.sort(list, new Comparator<Drug>() {
	        @Override
	        public int compare(Drug drug1, Drug drug2)
	        {
	            return  drug1.getName().compareTo(drug2.getName());
	        }
	    });
		
		JSONObject obj = new JSONObject();
		
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
        
        List<Drug> sublist = null;
        if(currentpage * 5 > list.size()){
        sublist = list.subList((currentpage - 1) * 5, list.size());
        }else{
        sublist = list.subList((currentpage - 1) * 5, currentpage * 5);	
        }
		
		obj.put("list", sublist);
		obj.put("currentpage", currentpage);
		obj.put("pagebegin", pagebegin);
		obj.put("pageend", pageend);
		obj.put("organizationname", organizationname);
		
		PrintWriter out = resp.getWriter();
        out.println(obj);
        
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
    }
	
	@RequestMapping(value = "/adddrug.htm", method=RequestMethod.POST)
	protected void doAddDrug(@ModelAttribute("drug")Drug drug, HttpServletRequest req, HttpServletResponse resp){

		try{
		
		JSONObject obj = new JSONObject();
		String organizationname = req.getParameter("organizationname");
		Organization organization = organizationdao.get(organizationname);
		
		if(drug.getName().equals("")){
			obj.put("error", "Drug Name required");
			PrintWriter out = resp.getWriter();
	        out.println(obj);
	        return;
		}
		
		if(drug.getPharma().equals("")){
			obj.put("error", "Pharma required");
			PrintWriter out = resp.getWriter();
	        out.println(obj);
	        return;
		}
		
		
		if(!drug.getPrice().matches("^\\d+$")){
			obj.put("error", "Please input a correct price");
			PrintWriter out = resp.getWriter();
	        out.println(obj);
	        return;
		}else{
			int number = Integer.parseInt(drug.getPrice());
			if(number == 0){
			obj.put("error", "Please input a correct price");
			PrintWriter out = resp.getWriter();
	        out.println(obj);
	        return;
			}
		}
		
		if(!drug.getQuantity().matches("^\\d+$")){	
			obj.put("error", "Please input a correct quantity");
			PrintWriter out = resp.getWriter();
	        out.println(obj);
	        return;
		}else{
			int number = Integer.parseInt(drug.getQuantity());
			if(number == 0){
			obj.put("error", "Please input a correct quantity");
			PrintWriter out = resp.getWriter();
	        out.println(obj);
	        return;
			}
		}
		
		organizationdao.addDrug(organization, drug);
		
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
    }
	
	@RequestMapping(value = "/updatedrug.htm", method=RequestMethod.POST)
	protected void doUpdateDrug(@RequestParam("drugid")String drugid, @ModelAttribute("drug")Drug drug, HttpServletRequest req, HttpServletResponse resp){

		try{
		
		JSONObject obj = new JSONObject();
		DrugDAO drugdao = new DrugDAO();
		
		if(drug.getName().equals("")){
			obj.put("error", "Drug Name required");
			PrintWriter out = resp.getWriter();
	        out.println(obj);
	        return;
		}
		
		if(drug.getPharma().equals("")){
			obj.put("error", "Pharma required");
			PrintWriter out = resp.getWriter();
	        out.println(obj);
	        return;
		}
		
		
		if(!drug.getPrice().matches("^\\d+$")){
			obj.put("error", "Please input a correct price");
			PrintWriter out = resp.getWriter();
	        out.println(obj);
	        return;
		}else{
			int number = Integer.parseInt(drug.getPrice());
			if(number == 0){
			obj.put("error", "Please input a correct price");
			PrintWriter out = resp.getWriter();
	        out.println(obj);
	        return;
			}
		}
		
		if(!drug.getQuantity().matches("^\\d+$")){	
			obj.put("error", "Please input a correct quantity");
			PrintWriter out = resp.getWriter();
	        out.println(obj);
	        return;
		}else{
			int number = Integer.parseInt(drug.getQuantity());
			if(number == 0){
			obj.put("error", "Please input a correct quantity");
			PrintWriter out = resp.getWriter();
	        out.println(obj);
	        return;
			}
		}
		
		int newdrugid = Integer.parseInt(drugid);
		Drug newdrug = drugdao.get(newdrugid);
		drugdao.update(newdrug, drug.getName(), drug.getPharma(), drug.getPrice(), drug.getQuantity());
		
        return;
        
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
    }
	
	@RequestMapping(value = "/deletedrug.htm", method=RequestMethod.POST)
	protected void doDeleteDrug(HttpServletRequest req, HttpServletResponse resp){

		try{
			
		DrugDAO drugdao = new DrugDAO();
		int drugid = Integer.parseInt(req.getParameter("drugid"));
		Drug drug = drugdao.get(drugid);
		drugdao.delete(drug);
		
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 

    }

}
