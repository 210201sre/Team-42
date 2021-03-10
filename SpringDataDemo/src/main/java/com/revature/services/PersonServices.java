package com.revature.services;

import java.sql.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.controllers.UserController;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.User;
import com.revature.repositories.CustomerDAO;
import com.revature.repositories.EmployeeDAO;
import com.revature.repositories.UserDAO;

@Service
public class PersonServices {

	private static final Logger log=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private EmployeeDAO employeeDAO;
	@Autowired
	private CustomerDAO customerDAO;
	@Autowired
	private UserDAO userDAO;
	
	public Set<Employee> findAllEmployees() {
		return employeeDAO.findAll()
				.stream()
				.collect(Collectors.toSet());
	}
	
	public Employee findEmployeeById(int id) {
		return employeeDAO.findById(id)
				.orElseThrow( () -> new UserNotFoundException("No Employee found with id " + id));
	}
	
	public Employee insert(Employee e, int id) {
		MDC.put("event", "insert employee");
		MDC.put("userid", id);
		MDC.put("employeeid", e.getId());
		
		Date temp=new Date(System.currentTimeMillis());
		e.setDateHired(temp);
		Employee eTemp=employeeDAO.save(e);
		Optional<User> oU=userDAO.findById(id);
		User u=oU.get();
		u.setEmployee_data(eTemp);
		userDAO.save(u);
		log.info("employee info added");
		MDC.clear();
		return eTemp;
	}
	

	
	public Set<Customer> findAllCustomers() {
		return customerDAO.findAll()
				.stream()
				.collect(Collectors.toSet());
	}
	
	public Customer findCustomerById(int id) {
		return customerDAO.findById(id)
				.orElseThrow( () -> new UserNotFoundException("No Employee found with id " + id));
	}
	
	public Customer insert(Customer c, int id) {
		
		MDC.put("event", "insert customer");
		MDC.put("userid", id);
		MDC.put("employeeid", c.getId());
		
		Date temp=new Date(System.currentTimeMillis());
		c.setDateJoined(temp);
		Customer cTemp=customerDAO.save(c);
		Optional<User> oU=userDAO.findById(id);
		User u=oU.get();
		u.setCustomer_data(cTemp);
		userDAO.save(u);
		log.info("customer info added");
		MDC.clear();
		return cTemp;
	}
	
}
