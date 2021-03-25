package com.revature.services;

import java.util.HashSet;
import java.util.List;
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
import com.revature.models.CheckingsAccount;
import com.revature.models.User;
import com.revature.repositories.UserDAO;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Service
public class UserService {
	private MeterRegistry meterRegistry;
	private Counter successJdbcCounter;
	private Counter jdbcCounter;
	
	public UserService (MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
        successJdbcCounter = meterRegistry.counter("jdbc connected", "type", "success");;
    	jdbcCounter = meterRegistry.counter("jdbc connected", "type", "connect");;
	}
	
	@Autowired
	private UserDAO userDAO;
	
	private static final Logger log=LoggerFactory.getLogger(UserController.class);
	
	public Set<User> findAll() {
		Set<User> users = null;
		jdbcCounter.increment(1);
		try {
			users = userDAO.findAll()
			.stream()
			.collect(Collectors.toSet());
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException e) {
			log.info(e.toString());
		}
		return users;
	}
	
	public User findById(int id) {
		User u = null;
		jdbcCounter.increment(1);
		try {
			u = userDAO.findById(id)
			.orElseThrow( () -> new UserNotFoundException("No user found with id " + id));
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException e) {
			log.info(e.toString());
		}
		return u;
	}
	
	public User findByUsername(String username) {
		User u = null;
		jdbcCounter.increment(1);
		try {
			u = userDAO.findByUsername(username)
			.orElseThrow( () -> new UserNotFoundException("No user found with username " + username));
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException e) {
			log.info(e.toString());
		}
		return u;
	}
	
	public User insert(User u) {
		jdbcCounter.increment(1);
		try {
			u = userDAO.save(u);
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException e) {
			log.info(e.toString());
		}
		return u;
	}
	
	public Set<User> paySalary() {
		MDC.put("event", "Salary");
		List<User> users = null;
		users = userDAO.findAll();
		Set<User> returnusers=new HashSet<>();
		for(User u: users) {
			if(u.isEmployee())
			{
				if(u.getCAccounts().size()>0) {
					u.getCAccounts().get(0).setBalance(u.getCAccounts().get(0).getBalance()+(u.getEmployee_data().getSalary()/24));
					returnusers.add(u);
					jdbcCounter.increment(1);
					try {
						userDAO.save(u);
						successJdbcCounter.increment(1);
					}
					catch (JDBCConnectionException e) {
						log.info(e.toString());
					}
					
				}
					
			}
		}
		log.info("salary paid");
		MDC.clear();
		return returnusers;
		
	}
	
	
}
