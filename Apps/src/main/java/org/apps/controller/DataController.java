package org.apps.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String getWelcomePage() {
		return "welcome";
	}
	
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
