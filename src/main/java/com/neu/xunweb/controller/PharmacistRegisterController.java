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

import com.neu.xunweb.dao.OrganizationDAO;
import com.neu.xunweb.dao.PharmacistAccountDAO;
import com.neu.xunweb.pojo.Account;
import com.neu.xunweb.pojo.Organization;
import com.neu.xunweb.pojo.PharmacistAccount;

@Controller
public class PharmacistRegisterController {
	
	@Autowired
	@Qualifier("pharmacistaccountValidator")
	PharmacistAccountValidator pharmacistaccountValidator;
	
	@Autowired
	@Qualifier("pharmacistaccountdao")
	PharmacistAccountDAO pharmacistaccountdao;
	
	@Autowired
	@Qualifier("organizationdao")
	OrganizationDAO organizationdao;
	
	@InitBinder("pharmacistaccount")
	private void initBinder(WebDataBinder binder){
		binder.setValidator(pharmacistaccountValidator);
	}
	
	@RequestMapping(value = "/pharmacistregisteraccount.htm", method=RequestMethod.POST)
	protected String doNextAction(@RequestParam("city")String city, @RequestParam("type")String type, @ModelAttribute("pharmacistaccount")PharmacistAccount pharmacistaccount, BindingResult result, Model model, HttpSession session){
		
		pharmacistaccountValidator.validate(pharmacistaccount, result);
		if(result.hasErrors()){
			return "PharmacistInformation";
		}
		
		session.setAttribute("city", city);
		session.setAttribute("type", type);
		session.setAttribute("pharmacistaccount", pharmacistaccount);
		
        return "PharmacistSelectOrganization";
		}
	
	@RequestMapping(value = "/pharmacistselectorganization.htm", method=RequestMethod.POST)
	protected String doSubmitAction(Model model, HttpServletRequest req, HttpServletResponse resp, HttpSession session){
		
		try{
		
		String name = req.getParameter("name");
		Organization organization = organizationdao.get(name);
		
		Account account = (Account) session.getAttribute("account");
		PharmacistAccount pharmacistaccount = (PharmacistAccount) session.getAttribute("pharmacistaccount");
		
		pharmacistaccountdao.create(account, organization, pharmacistaccount.getName(), pharmacistaccount.getTelephone());
		
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
		
        return "Success";
		}

}
