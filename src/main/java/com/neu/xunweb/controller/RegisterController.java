package com.neu.xunweb.controller;

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

import com.neu.xunweb.dao.AccountDAO;
import com.neu.xunweb.pojo.Account;
import com.neu.xunweb.pojo.DoctorAccount;
import com.neu.xunweb.pojo.PatientAccount;
import com.neu.xunweb.pojo.PharmacistAccount;


@Controller
public class RegisterController {
	
	@Autowired
	@Qualifier("accountValidator")
	AccountValidator accountValidator;
	
	@Autowired
	@Qualifier("accountdao")
	AccountDAO accountdao;
	
	@InitBinder("account")
	private void initBinder(WebDataBinder binder){
		binder.setValidator(accountValidator);
	}
	
	@RequestMapping(value = "/registeraccount.htm", method=RequestMethod.POST)
	protected String doSubmitAction(@RequestParam("cpassword")String cpassword, @ModelAttribute("account")Account account, BindingResult result, Model model, HttpSession session){
		
		accountValidator.validate(account, result);
		if(result.hasErrors()){
			return "Register";
		}
		
		if(!account.getPassword().equals(cpassword)){
			model.addAttribute("error1", "Please confirm your password");
			return "Register";
		}
		
		Account realaccount = null;
		
		try{
		
		realaccount = accountdao.get(account.getUsername());
		
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
		
		if(realaccount != null){
			model.addAttribute("error1", "This username was already existed");
			return "Register";
		}
		
		if(account.getRole().equals("Doctor")){
			model.addAttribute("doctoraccount", new DoctorAccount());
			session.setAttribute("account", account);
			return "DoctorInformation";
		}else if(account.getRole().equals("Patient")){
			model.addAttribute("patientaccount", new PatientAccount());
			session.setAttribute("account", account);
			return "PatientInformation";
		}else if(account.getRole().equals("Pharmacist")){
			model.addAttribute("pharmacistaccount", new PharmacistAccount());
			session.setAttribute("account", account);
			return "PharmacistInformation";
		}else{
			model.addAttribute("error1", "Error");
			return "Register";
	}
    }

}
