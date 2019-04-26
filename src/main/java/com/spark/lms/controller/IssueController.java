package com.spark.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spark.lms.service.IssueService;

@Controller
@RequestMapping(value = "/issue")
public class IssueController {

	@Autowired
	private IssueService issueService;
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newIssuePage() { 
		return "/issue/form";
	}
	
}
