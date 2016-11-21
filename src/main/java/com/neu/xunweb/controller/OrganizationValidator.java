package com.neu.xunweb.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.neu.xunweb.pojo.Organization;

public class OrganizationValidator implements Validator{

	@Override
	public boolean supports(Class aClass) {
		// TODO Auto-generated method stub
		return aClass.equals(Organization.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		Organization organization = (Organization) obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.invalid.organization", "Organization Name Required");
		
	}

}
