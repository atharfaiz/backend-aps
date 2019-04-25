package org.apps.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
	
	public ResponseEntity<List<Object>> addCustomer(){
		ResponseEntity<List<Object>> response=null;
		try {
			response=new ResponseEntity<>(null,HttpStatus.OK);
		}catch(Exception e) {
			
		}
		return response;
	}

}
