package org.apps.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apps.dto.Ac;
import org.apps.dto.MockVO;
import org.apps.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(tags = "Master Data API")
@RestController
public class DataController {
	
	ResourceBundle i18n = ResourceBundle.getBundle("ActionMessages");
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	DataService dataService;
	
	@ApiOperation(value = "Master Data api", notes = "Test api")
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView getWelcomePage(HttpServletRequest request,HttpServletResponse response) {
		List<Ac> acList = new ArrayList<>();
		Ac a=new Ac(1, "A");
		Ac a1=new Ac(1, "Aa");
		Ac a2=new Ac(1, "B");
		Ac a3=new Ac(1, "Bc");
		Ac a4=new Ac(1, "C");
		Ac a5=new Ac(1, "D");
		acList.add(a);
		acList.add(a1);
		acList.add(a2);
		acList.add(a3);
		acList.add(a4);
		acList.add(a5);
		request.setAttribute("AC", acList);
		return new ModelAndView("welcome");
	}
	
	@ApiOperation(value = "Master Data api", notes = "Test api")
	@RequestMapping(value="/hello",method=RequestMethod.GET)
	public Object getHelloPage() {
		return dataService.findObject();
	}
	
	public String test() {
		return "";
	}
	
	@Scheduled(fixedDelay =30000)
	public void getMessage() {
		System.out.println("HELLO SCHEDULER");
	}
	
	
	
	@RequestMapping(value="/que",method = RequestMethod.GET)
	public PriorityQueue<Integer> que(){
		PriorityQueue<Integer> qu= new PriorityQueue<Integer>();
		qu.offer(11);
		qu.offer(1);
		qu.offer(33);
		qu.offer(3);
		qu.offer(2);
		qu.offer(7);
		return qu;
	}
	
	@ApiOperation(value = "Maata api", notes = "i")
	@RequestMapping(value="/p",method = RequestMethod.POST)
	public MockVO returnMock(@RequestBody MockVO vo) {
		return vo;
	}
	
	
	
	

}
