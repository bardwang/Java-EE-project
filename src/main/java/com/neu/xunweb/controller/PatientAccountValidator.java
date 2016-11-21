package com.neu.xunweb.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.neu.xunweb.pojo.PatientAccount;

public class PatientAccountValidator implements Validator{

	@Override
	public boolean supports(Class aClass) {
		// TODO Auto-generated method stub
		return aClass.equals(PatientAccount.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		PatientAccount patientaccount = (PatientAccount) obj;
		
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"  
				   + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"; 
		
		if(!patientaccount.getEmail().equals("") && !patientaccount.getEmail().matches(EMAIL_PATTERN)){
		errors.rejectValue("email", "error.invalid.email", "Email Invalid");
		}
		
		if(!patientaccount.getPhone().equals("") && patientaccount.getPhone().length() != 10 && patientaccount.getPhone().length() != 11){
		errors.rejectValue("phone", "error.invalid.phone", "Please type a right phone number");	
		}
		
		ValidationUtils.rejectIfEmpty(errors, "name", "error.invalid.name", "Name Required");
		ValidationUtils.rejectIfEmpty(errors, "address", "error.invalid.address", "Address Required");
		ValidationUtils.rejectIfEmpty(errors, "phone", "error.invalid.phone", "Phone Required");
		ValidationUtils.rejectIfEmpty(errors, "email", "error.invalid.email", "Email Required");
	}

}
