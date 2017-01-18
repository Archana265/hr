package com.opus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.opus.model.Employee;

@RestController
public class HomeController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value = "/hr/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
	public ResponseEntity<Employee> getEmployeeJSONById(@PathVariable Long id) 
	{
		ResponseEntity<Employee> response = null;
		try{
			response =  this.restTemplate.exchange("http://employeeConfigEurekaClient/employee/{id}", HttpMethod.GET, null, Employee.class,id);
		}catch(HttpClientErrorException exception){
			return new ResponseEntity(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(response, HttpStatus.OK);
	}

}
