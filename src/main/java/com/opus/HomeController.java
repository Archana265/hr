package com.opus;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HomeController {
	
	@RequestMapping(value = "/hr/employee", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
	public ResponseEntity getAllEmployeesJSON(Model model) 
	{
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl
		  = "http://localhost:8005/employee";
		ResponseEntity<String> response
		  = restTemplate.getForEntity(resourceUrl + "/1", String.class);
		return new ResponseEntity(response, HttpStatus.OK);
	}

}
