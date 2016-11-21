package com.neu.xunweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.neu.xunweb.dao.DoctorAccountDAO;
import com.neu.xunweb.dao.OrganizationDAO;
import com.neu.xunweb.pojo.Account;
import com.neu.xunweb.pojo.DoctorAccount;
import com.neu.xunweb.pojo.Organization;


@Controller
public class DoctorRegisterController {
	
	@Autowired
	@Qualifier("doctoraccountValidator")
	DoctorAccountValidator doctoraccountValidator;
	
	@Autowired
	@Qualifier("organizationdao")
	OrganizationDAO organizationdao;
	
	@Autowired
	@Qualifier("doctoraccountdao")
	DoctorAccountDAO doctoraccountdao;
	
	@InitBinder("doctoraccount")
	private void initBinder(WebDataBinder binder){
		binder.setValidator(doctoraccountValidator);
	}
	
	@RequestMapping(value = "/doctorregisteraccount.htm", method=RequestMethod.POST)
	protected String doNextAction(@RequestParam("city")String city, @RequestParam("type")String type, @ModelAttribute("doctoraccount")DoctorAccount doctoraccount, BindingResult result, Model model, HttpSession session){
		
		doctoraccountValidator.validate(doctoraccount, result);
		if(result.hasErrors()){
			return "DoctorInformation";
		}
		
		session.setAttribute("city", city);
		session.setAttribute("type", type);
		session.setAttribute("doctoraccount", doctoraccount);
		
        return "DoctorSelectOrganization";
		}
	
	@RequestMapping(value = "/doctorselectorganization.htm", method=RequestMethod.POST)
	protected String doSubmitAction(Model model, HttpServletRequest req, HttpServletResponse resp, HttpSession session){
		
		try{
			
		String name = req.getParameter("name");
		Organization organization = organizationdao.get(name);
		
		Account account = (Account) session.getAttribute("account");
		DoctorAccount doctoraccount = (DoctorAccount) session.getAttribute("doctoraccount");
		
		doctoraccountdao.create(account, organization, doctoraccount.getName(), 
				doctoraccount.getTelephone(), doctoraccount.getConcentration(),
				doctoraccount.getYearsofexperience(), doctoraccount.getBackground());
		
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
		
        return "Success";
		}

}
