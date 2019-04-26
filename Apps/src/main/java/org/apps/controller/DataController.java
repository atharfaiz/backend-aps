package org.apps.controller;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(tags = "Master Data API")
@RestController
public class DataController {
	
	ResourceBundle i18n = ResourceBundle.getBundle("ActionMessages");
	
	@Autowired
	MessageSource messageSource;
	
	@ApiOperation(value = "Master Data api", notes = "Test api")
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String getWelcomePage() {
		return "welcome";
	}
	
	@ApiOperation(value = "Master Data api", notes = "Test api")
	@RequestMapping(value="/hello",method=RequestMethod.GET)
	public String getHelloPage() {
		return "hello";
	}
	
	public String test() {
		return "";
	}
	
	@Scheduled(fixedDelay =30000)
	public void getMessage() {
		System.out.println("HELLO SCHEDULER");
	}
	
	

}
