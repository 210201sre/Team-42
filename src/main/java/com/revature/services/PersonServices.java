package com.revature.services;

import java.sql.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.exception.JDBCConnectionException;
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

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Service
public class PersonServices {

	private static final Logger log=LoggerFactory.getLogger(UserController.class);
	private MeterRegistry meterRegistry;
	private Counter successJdbcCounter;
	private Counter jdbcCounter;
	
	public PersonServices (MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
        successJdbcCounter = meterRegistry.counter("jdbc connected", "type", "success");;
    	jdbcCounter = meterRegistry.counter("jdbc connected", "type", "connect");;
	}
	
	@Autowired
	private EmployeeDAO employeeDAO;
	@Autowired
	private CustomerDAO customerDAO;
	@Autowired
	private UserDAO userDAO;
	
	public Set<Employee> findAllEmployees() {
		Set<Employee> eSet = null;
		jdbcCounter.increment(1);
		try {
			eSet = employeeDAO.findAll()
					.stream()
					.collect(Collectors.toSet());
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException e) {
			log.info(e.toString());
		}
		return eSet;
	}
	
	public Employee findEmployeeById(int id) {
		Employee e = null;
		jdbcCounter.increment(1);
		try {
			e = employeeDAO.findById(id)
					.orElseThrow( () -> new UserNotFoundException("No Employee found with id " + id));
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		return e;
	}
	
	public Employee insert(Employee e, int id) {
		MDC.put("event", "insert employee");
		MDC.put("userid", id);
		MDC.put("employeeid", e.getId());
		
		Date temp=new Date(System.currentTimeMillis());
		e.setDateHired(temp);
		Employee eTemp=e;
		jdbcCounter.increment(1);
		try {
			eTemp=employeeDAO.save(e);
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		Optional<User> oU=null;
		jdbcCounter.increment(1);
		try {
			oU=userDAO.findById(id);
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		User u=oU.get();
		u.setEmployee_data(eTemp);
		u.setEmployee(true); //added to set user's employee value to true to ensure user data is updated
		jdbcCounter.increment(1);
		try {
			userDAO.save(u);
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		log.info("employee info added");
		MDC.clear();
		return eTemp;
	}
	

	
	public Set<Customer> findAllCustomers() {
		Set<Customer> cSet = null;
		jdbcCounter.increment(1);
		try {
			cSet = customerDAO.findAll()
					.stream()
					.collect(Collectors.toSet());
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		return cSet;
	}
	
	public Customer findCustomerById(int id) {
		Customer c = null;
		jdbcCounter.increment(1);
		try {
			c = customerDAO.findById(id)
					.orElseThrow( () -> new UserNotFoundException("No Employee found with id " + id));
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		return c;
	}
	
	public Customer insert(Customer c, int id) {
		
		MDC.put("event", "insert customer");
		MDC.put("userid", id);
		MDC.put("employeeid", c.getId());
		
		Date temp=new Date(System.currentTimeMillis());
		c.setDateJoined(temp);
		Customer cTemp=null;
		jdbcCounter.increment(1);
		try {
			cTemp=customerDAO.save(c);
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		Optional<User> oU=null;
		jdbcCounter.increment(1);
		try {
			oU=userDAO.findById(id);
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		User u=oU.get();
		u.setCustomer_data(cTemp);
		jdbcCounter.increment(1);
		try {
			userDAO.save(u);
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		log.info("customer info added");
		MDC.clear();
		return cTemp;
	}
	
}
