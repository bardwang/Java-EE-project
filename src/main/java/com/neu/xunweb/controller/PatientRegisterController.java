package com.neu.xunweb.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neu.xunweb.dao.PatientAccountDAO;
import com.neu.xunweb.pojo.Account;
import com.neu.xunweb.pojo.PatientAccount;

@Controller
public class PatientRegisterController {
	
	@Autowired
	@Qualifier("patientaccountValidator")
	PatientAccountValidator patientaccountValidator;
	
	@Autowired
	@Qualifier("patientaccountdao")
	PatientAccountDAO patientaccountdao;
	
	@InitBinder("patientaccount")
	private void initBinder(WebDataBinder binder){
		binder.setValidator(patientaccountValidator);
	}
	
	@RequestMapping(value = "/patientregisteraccount.htm", method=RequestMethod.POST)
	protected String doSubmitAction(@ModelAttribute("patientaccount")PatientAccount patientaccount, BindingResult result, HttpSession session){
		
		try{
		
		patientaccountValidator.validate(patientaccount, result);
		if(result.hasErrors()){
			return "PatientInformation";
		}
		
		Account account = (Account) session.getAttribute("account");
		patientaccountdao.create(account, patientaccount.getName(), patientaccount.getAddress(),
								patientaccount.getPhone(), patientaccount.getEmail());
		
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
		
        return "Success";
		}

}
