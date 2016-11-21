package com.neu.xunweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ManagerSelectController {

	@RequestMapping(value = "/goapprovedoctor.htm", method=RequestMethod.POST)
	protected String manageNewDoctorAction(Model model){

        return "ApproveDoctor";
    }
	
	@RequestMapping(value = "/goapprovepharmacist.htm", method=RequestMethod.POST)
	protected String manageNewPharmacistAction(Model model){

        return "ApprovePharmacist";
    }
	
	@RequestMapping(value = "/goconnectorganization.htm", method=RequestMethod.POST)
	protected String connectOrganizationAction(Model model){

        return "ConnectOrganization";
    }

}
