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
import org.springframework.web.servlet.ModelAndView;

import com.neu.xunweb.dao.AccountDAO;
import com.neu.xunweb.dao.DoctorAccountDAO;
import com.neu.xunweb.dao.PatientAccountDAO;
import com.neu.xunweb.dao.PharmacistAccountDAO;
import com.neu.xunweb.pojo.Account;
import com.neu.xunweb.pojo.Appointment;
import com.neu.xunweb.pojo.DoctorAccount;
import com.neu.xunweb.pojo.Drug;
import com.neu.xunweb.pojo.PatientAccount;
import com.neu.xunweb.pojo.PharmacistAccount;
import com.neu.xunweb.springview.PatientPdfView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	@Qualifier("accountValidator")
	AccountValidator accountValidator;
	
	@Autowired
	@Qualifier("accountdao")
	AccountDAO accountdao;
	
	@Autowired
	@Qualifier("doctoraccountdao")
	DoctorAccountDAO doctoraccountdao;
	
	@Autowired
	@Qualifier("pharmacistaccountdao")
	PharmacistAccountDAO pharmacistaccountdao;
	
	@Autowired
	@Qualifier("patientaccountdao")
	PatientAccountDAO patientaccountdao;
	
	@InitBinder("account")
	private void initBinder(WebDataBinder binder){
		binder.setValidator(accountValidator);
	}
	
	@RequestMapping(value = "/login.htm", method=RequestMethod.POST)
	protected ModelAndView doLoginAction(@ModelAttribute("account")Account account, BindingResult result, Model model, HttpSession session, HttpServletRequest req, HttpServletResponse resp){
		
		Account realaccount = null;
		try{
			realaccount = accountdao.get(account.getUsername(), account.getPassword());
		}catch (Exception e){
			System.err.println("Caught Exception: " + e.getMessage());
		} 
		
		accountValidator.validate(realaccount, result);
		if(result.hasErrors()){					
			return new ModelAndView("Home");
		}		
		
		if(realaccount == null){
			model.addAttribute("error1", "This account does not exist. Please try again.");
			return new ModelAndView("Home");
		}
		
		if(realaccount.getRole().equals("Doctor")){
			DoctorAccount doctoraccount = null;
			try{
			doctoraccount = doctoraccountdao.get(realaccount.getAccountid());
			}catch(Exception e){
				System.err.println("Caught Exception: " + e.getMessage());
			}
			if(doctoraccount.getStatus() == 0){
				return new ModelAndView("Pending");
			}else{
				session.setAttribute("accountid", realaccount.getAccountid());
				return new ModelAndView("DoctorPage");
			}
		}
		
		if(realaccount.getRole().equals("Pharmacist")){
			PharmacistAccount pharmacistaccount = null;
			try{
				pharmacistaccount = pharmacistaccountdao.get(realaccount.getAccountid());
			}catch(Exception e){
				System.err.println("Caught Exception: " + e.getMessage());
			}
			
			if(pharmacistaccount.getStatus() == 0){
				return new ModelAndView("Pending");
			}else{
				session.setAttribute("accountid", realaccount.getAccountid());
				req.setAttribute("organizationname", pharmacistaccount.getOrganization().getName());
				model.addAttribute(new Drug());
				return new ModelAndView("ManageDrug");
			}
		}
		
		if(realaccount.getRole().equals("Manager")){
			return new ModelAndView("ManagerAction");
		}
		
		if(realaccount.getRole().equals("Patient")){
			PatientAccount patientaccount = null;
			try{
				patientaccount = patientaccountdao.get(realaccount.getAccountid());
			}catch(Exception e){
				System.err.println("Caught Exception: " + e.getMessage());
			}
			if(patientaccount.getAppointment() != null){
				Appointment app = patientaccount.getAppointment();
				String patientname = app.getPatientname();
				String doctorname = app.getDoctoraccount().getName();
				String date = app.getDate();
				String time = app.getTime();
				Long reservation = app.getReservationnumber();
				String organizationname = app.getDoctoraccount().getOrganization().getName();
				PatientPdfView pdf = new PatientPdfView();
				pdf.setPatientname(patientname);
				pdf.setDoctorname(doctorname);
				pdf.setDate(date);
				pdf.setTime(time);
				pdf.setReservationnumber(reservation);
				pdf.setAddress(organizationname);
				return new ModelAndView(pdf);
			}else{
			session.setAttribute("patientid", realaccount.getAccountid());
			return new ModelAndView("PatientPage");
			}
		}
		
        return new ModelAndView("Error");
    }
	
	@RequestMapping(value = "/register.htm")
	protected String doRegisterAction(Model model) throws Exception{

		model.addAttribute(new Account());
        return "Register";
    }
	
	@RequestMapping(value = "/home.htm")
	protected String home(Model model) throws Exception{
		model.addAttribute(new Account());
        return "Home";
    }
	
	@RequestMapping(value = "/init.htm")
	protected String init(Model model) throws Exception{
		
		if(accountdao.get("manager", "manager") == null){
		accountdao.create("manager", "manager", "Manager");
		}
		model.addAttribute(new Account());
		
        return "Home";
    }
}
