package com.neu.xunweb.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.neu.xunweb.pojo.PharmacistAccount;

public class PharmacistAccountValidator implements Validator{

	@Override
	public boolean supports(Class aClass) {
		// TODO Auto-generated method stub
		return aClass.equals(PharmacistAccount.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		PharmacistAccount pharmacistaccount = (PharmacistAccount) obj;
		
		if(!pharmacistaccount.getTelephone().equals("") && pharmacistaccount.getTelephone().length() != 10 && pharmacistaccount.getTelephone().length() != 11){
			errors.rejectValue("telephone", "error.invalid.telephone", "Please type a right telephone number");	
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.invalid.name", "Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telephone", "error.invalid.telephone", "Telephone Required");
		
	}

}
