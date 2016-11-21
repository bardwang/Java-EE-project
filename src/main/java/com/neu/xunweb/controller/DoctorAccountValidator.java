package com.neu.xunweb.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.neu.xunweb.pojo.DoctorAccount;

public class DoctorAccountValidator implements Validator{

	@Override
	public boolean supports(Class aClass) {
		// TODO Auto-generated method stub
		return aClass.equals(DoctorAccount.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		DoctorAccount doctoraccount = (DoctorAccount) obj;
		
		if(!doctoraccount.getTelephone().equals("") && doctoraccount.getTelephone().length() != 10 && doctoraccount.getTelephone().length() != 11){
			errors.rejectValue("telephone", "error.invalid.telephone", "Please type a right telephone number");	
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.invalid.name", "Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telephone", "error.invalid.telephone", "Telephone Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "concentration", "error.invalid.concentration", "Concentration Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "background", "error.invalid.background", "Background Required");
	}

}
