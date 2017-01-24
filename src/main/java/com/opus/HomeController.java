package com.opus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.opus.model.Department;
import com.opus.model.Employee;

@RestController
public class HomeController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(groupKey = "notification-service", fallbackMethod = "fallbackforgetEmployeeJSONById")
	@RequestMapping(value = "/hr/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
	public ResponseEntity<Employee> getEmployeeJSONById(@PathVariable Long id) 
	{
		ResponseEntity<Employee> response = null;
		try{
			response =  this.restTemplate.exchange("http://employeeConfigEurekaClient/employee/{id}", HttpMethod.GET, null, Employee.class,id);
		}catch(HttpClientErrorException exception){
			return new ResponseEntity<Employee>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(response, HttpStatus.OK);
	}
	@HystrixCommand(groupKey = "notification-service", fallbackMethod = "fallbackforgetEmployeeAndDept")
	@RequestMapping(value = "/hr/department/{d_id}", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
	public ResponseEntity<Department> getEmployeeAndDept(@PathVariable("d_id") String d_id) 
	{
		ResponseEntity<Department> responseDept = null;
		try{
		//ResponseEntity<Employee> responseEmp =  this.restTemplate.exchange("http://employeeApp/employee/{id}" ,HttpMethod.GET, null, Employee.class,id);
		responseDept =  this.restTemplate.exchange("http://DepartmentApp/department/{d_id}",HttpMethod.GET, null, Department.class,d_id);
		}catch(HttpClientErrorException exception){
			return new ResponseEntity<Department>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(responseDept, HttpStatus.OK);
	}
	@RequestMapping(value = "/hr/test", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
	@HystrixCommand(groupKey = "notification-service", fallbackMethod = "statusPageDown")
	public ResponseEntity hytrixTest() {
	    throw new RuntimeException("Simulating downstream system failure");
	}
	 public ResponseEntity statusPageDown() {
	        return new ResponseEntity(HttpStatus.OK);
	    }
	public ResponseEntity<Employee> fallbackforgetEmployeeJSONById(@PathVariable Long id) 
	{
		return new ResponseEntity("from fall back of getEmployeeJSONById", HttpStatus.OK);
	}
	public ResponseEntity<Department> fallbackforgetEmployeeAndDept(@PathVariable("d_id") String d_id) 
	{
		return new ResponseEntity("from fall back of getEmployeeAndDept", HttpStatus.OK);
	}
	
}
