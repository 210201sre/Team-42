package com.revature.services;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

@Service
public class UserService {
	
	/*fields*/
	private static final Logger log=LoggerFactory.getLogger(UserController.class);
	
	@Autowired private UserDAO userDAO;
	
	/*Create methods*/
	public User insert(User u) {
		return userDAO.save(u);
	}
	
	/*Read methods*/
	public Set<User> findAll() {
		return userDAO.findAll()
				.stream()
				.collect(Collectors.toSet());
	}
	
	public User findById(int id) {
		return userDAO.findById(id)
				.orElseThrow( () -> new UserNotFoundException("No user found with id " + id));
	}
	
	public User findByUsername(String username) {
		return userDAO.findByUsername(username).orElseThrow( () -> 
				new UserNotFoundException("No user found with username " + username));
	}
	
	/*Update methods*/
	public Set<User> paySalary() {
		MDC.put("event", "Salary");
		Set<User> users = this.findAll();
		Set<User> returnusers = new HashSet<>();
		for(User u : users) {
			if(u.isEmployee()) {
				if(u.getCAccounts() != null) {
					u.getCAccounts()
							.get(0).setBalance(u
									.getCAccounts()
									.get(0).getBalance()+(u.getEmployee_data().getSalary()/24));
					returnusers.add(u);
					userDAO.save(u);
				}
			}
		}
		log.info("salary paid");
		MDC.clear();
		return returnusers;
	}
	/*Delete methods*/
}

	
	
	
	
	
	
	
	