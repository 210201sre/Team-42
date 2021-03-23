package com.revature.tests.user;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.revature.controllers.UserController;
import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.User;
import com.revature.services.UserService;

@SpringBootTest
class UserServiceTests {

	private static final Logger log=LoggerFactory.getLogger(UserController.class);
	
	@Autowired private UserService service;
	
	@Test void testFindAll() {
		Set<User> theUsers = service.findAll();
		assert(!theUsers.isEmpty());
	}
	
	@Test void testInsert() {
		User testUser = new User();
		testUser.setUsername("zargothrax");
		testUser.setPassword("1324");
		
		//check if test user already exists
		Set<User> userSet = service.findAll();
		for (User aUser : userSet) {
			if (aUser.getUsername() == testUser.getUsername()) {
				fail("username exists before insert");
			}
		}
		
		//check if insert worked
		service.insert(testUser);
		userSet = service.findAll();
		boolean found = false;
		for (User aUser : userSet) {
			if (aUser.getUsername() == testUser.getUsername()) {
				log.warn("\naUser: " + aUser.getUsername() + ".	 testUser: " + testUser.getUsername() + ".");
				found = true;
				break;
			}
			//fail("failed to insert testUser");
		}
		
		//check if deletion worked
		service.delete(testUser);
		userSet = service.findAll();
		boolean deleted = false;
		for (User aUser : userSet) {
			if (aUser.getUsername() == testUser.getUsername()) {
				fail("username not deleted");
				break;
			}
			deleted = true;
		}
		log.info("\nfound: " + found + "\ndeleted: " + deleted);
		assert (found && deleted);
	}
	
	 @Test void testPaySalary() {
		Set<User> userSet = service.findAll();
		User checkUser = null;
		for (User u : userSet) {
			if (!u.isEmployee()) {
				continue;
			} else {
				checkUser = u;
				break;
			}
		}
		if (checkUser == null) {
			fail("no employees found");
		}
		int checkUserId = checkUser.getId();
		double oldBalance = checkUser.getCAccounts().get(0).getBalance();
		
		service.paySalary();
		
		userSet = service.findAll();
		for (User u : userSet) {
			if (u.getId() == checkUserId) {
				checkUser = u;
				break;
			}
		}
		assert(oldBalance + checkUser.getEmployee_data().getSalary()/24 == 
				checkUser.getCAccounts().get(0).getBalance() );
	}
	
	
}
