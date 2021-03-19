package com.revature.controllers;

import java.util.Set;

import javax.validation.Valid;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Customer;
import com.revature.models.Employee;

import com.revature.services.PersonServices;

@RestController
public class PersonController {
	
	/*fields*/
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired private PersonServices personServices;
		
	/*GET methods*/
	
	@GetMapping("/customers")
	public ResponseEntity<Set<Customer>> findAllCustomers() {
		MDC.put("event", "find all customers");
		
		Set<Customer> allCustomers = personServices.findAllCustomers();
		if (allCustomers.isEmpty()) {
			log.warn("no customers found");
			MDC.clear();
			return ResponseEntity.noContent().build();
		}
		log.info("customers found");
		MDC.clear();
		return ResponseEntity.ok(allCustomers);
	}

	@GetMapping("/employees")
	public ResponseEntity<Set<Employee>> findAllEmployees() {
		MDC.put("event", "find all employee");

		Set<Employee> allEmployees = personServices.findAllEmployees();
		if (allEmployees.isEmpty()) {
			log.warn("no employees found");
			MDC.clear();
			return ResponseEntity.noContent().build();
		}
		log.info("employees found");
		MDC.clear();
		return ResponseEntity.ok(allEmployees);
	}
	
	/*POST methods*/
	@PostMapping("/employees/{id}")
	public ResponseEntity<Employee> insert(@Valid @RequestBody Employee e, @PathVariable("id") int id) {
		return ResponseEntity.ok(personServices.insert(e, id));
	}

	@PostMapping("/customers/{id}")
	public ResponseEntity<Customer> insert(@Valid @RequestBody Customer c, @PathVariable("id") int id) {
		return ResponseEntity.ok(personServices.insert(c, id));
	}
}
