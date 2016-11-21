package com.neu.xunweb.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
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

import com.neu.xunweb.dao.CDrugDAO;
import com.neu.xunweb.dao.DoctorAccountDAO;
import com.neu.xunweb.dao.DrugDAO;
import com.neu.xunweb.dao.OrganizationDAO;
import com.neu.xunweb.dao.PatientAccountDAO;
import com.neu.xunweb.pojo.Appointment;
import com.neu.xunweb.pojo.CDrug;
import com.neu.xunweb.pojo.DoctorAccount;
import com.neu.xunweb.pojo.Drug;
import com.neu.xunweb.pojo.Organization;
import com.neu.xunweb.pojo.PatientAccount;
import com.neu.xunweb.springview.DoctorPdfView;

@Controller
public class DoctorSelectController {
	
	@Autowired
	@Qualifier("doctoraccountdao")
	DoctorAccountDAO doctoraccountdao;
	
	@Autowired
	@Qualifier("patientaccountdao")
	PatientAccountDAO patientaccountdao;
	
	@Autowired
	@Qualifier("drugdao")
	DrugDAO drugdao;
	
	@Autowired
	@Qualifier("cdrugdao")
	CDrugDAO cdrugdao;
	
	@Autowired
	@Qualifier("organizationdao")
	OrganizationDAO organizationdao;
	
	@RequestMapping(value = "/appointmenttable.htm", method=RequestMethod.POST)
	protected void appointmenttable(HttpServletRequest req, HttpServletResponse resp){
		
		try{
		
		JSONObject obj = new JSONObject();
		HttpSession session = req.getSession();
		int accountid = (Integer) session.getAttribute("accountid");
		DoctorAccount da = doctoraccountdao.get(accountid);
		Set<Appointment> appointments = da.getAppointments();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		
		List<Appointment> list = new ArrayList<Appointment>();
		
		for(Appointment appointment: appointments){
			if(appointment.getDate().equals(date)){
				Appointment ap = new Appointment();
				ap.setDate(appointment.getDate());
				ap.setTime(appointment.getTime());
				ap.setPatientname(appointment.getPatientname());
				ap.setPatientid(appointment.getPatientaccount().getAccountid());
				ap.setReservationnumber(appointment.getReservationnumber());
				list.add(ap);
			}
		}
		
		obj.put("list", list);
		PrintWriter out = resp.getWriter();
        out.println(obj);
        
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
		
	}
	
	@RequestMapping(value = "/selectpatientdrug.htm", method=RequestMethod.POST)
	protected String selectpatientdrug(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
		
		String patientid = req.getParameter("patientid");
		session.setAttribute("patientid", patientid);
		
		return "DoctorSelectPatientDrug";
	}
	
	@RequestMapping(value = "/doctorfinish.htm")
	protected ModelAndView doctorfinish(HttpSession session){
		
		int accountid = (Integer) session.getAttribute("accountid");
		String patientid = (String) session.getAttribute("patientid");
		int patientidint = Integer.parseInt(patientid);
		PatientAccount pa = null;
		DoctorAccount da = null;
		
		try{
			
		pa = patientaccountdao.get(patientidint);
		da = doctoraccountdao.get(accountid);
		
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		}
		
		
		DoctorPdfView pdf = new DoctorPdfView();
		pdf.setDoctorname(da.getName());
		pdf.setPatientname(pa.getName());
		
		Set<CDrug> drugs = new HashSet<CDrug>();
		Set<CDrug> set = da.getCdrugs();
		for(CDrug cdrug: set){
			if(cdrug.getRemoved() == 1){
				continue;
			}
			CDrug drug = new CDrug();
			drug.setName(cdrug.getName());
			drug.setPharma(cdrug.getPharma());
			drug.setPrice(cdrug.getPrice());
			drug.setQuantity(cdrug.getQuantity());
			drugs.add(drug);
		}
		
		pdf.setCdrugs(drugs);
		
		try{
		doctoraccountdao.emptydrugs(da);
		Appointment app = pa.getAppointment();
		patientaccountdao.removeappointment(pa, app);
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		}
		
		return new ModelAndView(pdf);
	}
	
	@RequestMapping(value = "/addpatientdrug.htm", method=RequestMethod.POST)
	protected void addpatientdrug(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
		
		try{
		
		JSONObject obj = new JSONObject();
		String drugid = req.getParameter("drugid");
		int drugidint = Integer.parseInt(drugid);
		String quantity = req.getParameter("quantity");
		
		if(!quantity.matches("^\\d+$")){
			obj.put("error", "Please input a correct quantity");
			PrintWriter out = resp.getWriter();
	        out.println(obj);
	        return;
		}else{
			int number = Integer.parseInt(quantity);
			if(number == 0){
			obj.put("error", "Please input a correct quantity");
			PrintWriter out = resp.getWriter();
	        out.println(obj);
	        return;
			}
		}
		
		int newquantity = Integer.parseInt(quantity);
		
		Drug drug = drugdao.get(drugidint);
		int oldquantity = Integer.parseInt(drug.getQuantity());
		
		if(newquantity > oldquantity){
			obj.put("error", "Please input a correct quantity");
			PrintWriter out = resp.getWriter();
	        out.println(obj);
	        return;
		}
		
		int realquantity = oldquantity - newquantity;
		String realstrquantity = String.valueOf(realquantity);
		int accountid = (Integer) session.getAttribute("accountid");
		DoctorAccount da = doctoraccountdao.get(accountid);
		
		Set<CDrug> oldcart = da.getCdrugs();
		
		int alreadyhas = 0;
		for(CDrug cdrug: oldcart){
			if(cdrug.getName().equals(drug.getName()) && cdrug.getRemoved() == 0){
				String quantityincart = cdrug.getQuantity();
				int quantityincartint = Integer.parseInt(quantityincart);
				quantityincartint = quantityincartint + newquantity;
				quantityincart = String.valueOf(quantityincartint);
				cdrugdao.updatequantity(cdrug, quantityincart);
				drugdao.update(drug, drug.getName(), drug.getPharma(), drug.getPrice(), realstrquantity);
				alreadyhas = 1;
			}
		}
		
		if(alreadyhas == 0){
		CDrug newdrug = new CDrug();
		newdrug.setName(drug.getName());
		newdrug.setPharma(drug.getPharma());
		newdrug.setPrice(drug.getPrice());
		newdrug.setQuantity(String.valueOf(newquantity));	
		drugdao.update(drug, drug.getName(), drug.getPharma(), drug.getPrice(), realstrquantity);		
		doctoraccountdao.adddrug(da, newdrug);
		}
		
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/doctordrugtable.htm", method=RequestMethod.POST)
	protected void doctordrugtable(HttpServletRequest req, HttpServletResponse resp){
        
		try{
			
		HttpSession session = req.getSession();
		int accountid = (Integer) session.getAttribute("accountid");
		
		
		DoctorAccount doctoraccount = doctoraccountdao.get(accountid);
		Organization organization = doctoraccount.getOrganization();
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
        
        Set<CDrug> set2 = doctoraccount.getCdrugs();
		List<CDrug> list2 = new ArrayList<CDrug>();
		
		int totalprice = 0;
		for(CDrug cdrug: set2){
			if(cdrug.getRemoved() == 1){
				continue;
			}
			CDrug newcdrug = new CDrug();
			newcdrug.setDrugid(cdrug.getDrugid());
			newcdrug.setName(cdrug.getName());
			newcdrug.setPharma(cdrug.getPharma());
			newcdrug.setPrice(cdrug.getPrice());
			newcdrug.setQuantity(cdrug.getQuantity());
			int price = Integer.parseInt(cdrug.getPrice());
			int quantity = Integer.parseInt(cdrug.getQuantity());
			totalprice = totalprice + price * quantity;
			list2.add(newcdrug);
		}
		
		obj.put("list", sublist);
		obj.put("currentpage", currentpage);
		obj.put("pagebegin", pagebegin);
		obj.put("pageend", pageend);
		obj.put("organizationname", organizationname);
		obj.put("list2", list2);
		obj.put("totalprice", totalprice);
		
		PrintWriter out = resp.getWriter();
        out.println(obj);
        
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
    }

	@RequestMapping(value = "/deletepatientdrug.htm", method=RequestMethod.POST)
	protected void deletepatientdrug(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
		
		try{
		
		int accountid = (Integer) session.getAttribute("accountid");
		String quantity = (String) req.getParameter("quantity");
		int quantityint = Integer.parseInt(quantity);
		String name = (String) req.getParameter("name");
		String drugid = (String) req.getParameter("drugid");
		DoctorAccount da = doctoraccountdao.get(accountid);
		
		Organization organization = da.getOrganization();
		Set<Drug> set = organization.getDrugs();
		
		// drugs in the organization
		for(Drug drug: set){
			if(drug.getName().equals(name) && drug.getRemoved() == 0){
			int oldquantity = Integer.parseInt(drug.getQuantity());
			int realquantity = oldquantity + quantityint;
			String newquantitystr = String.valueOf(realquantity);
			drug.setQuantity(newquantitystr);
			organizationdao.addDrug(organization, drug);
			}
		}
	
		// remove drug in the cart
		int newdrugid = Integer.parseInt(drugid);
		CDrug cdrug = cdrugdao.get(newdrugid);
		cdrugdao.delete(cdrug);
		
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
        
	}
}
