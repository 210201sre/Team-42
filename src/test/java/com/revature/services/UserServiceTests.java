package com.revature.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.aspectj.lang.annotation.Before;
import org.jboss.logging.MDC;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.controllers.UserController;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.CheckingsAccount;
import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.User;
import com.revature.repositories.UserDAO;
import com.revature.services.UserService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class UserServiceTests {
	private static final Logger log=LoggerFactory.getLogger(UserController.class);
	
	@InjectMocks UserService service;
	
	@Mock UserDAO uDAO;
	
	@Test void findAllTest() {
		Optional<User> aUser = setTestUser();
		List<User> resultList = new ArrayList<User>();
		resultList.add(aUser.get());
		Mockito.when(uDAO.findAll()).thenReturn(resultList);
	
		Set<User> resultSet = service.findAll();
		
		assert(!resultSet.isEmpty());
	}
	
	@Test void findByIdTest() {
		Optional<User> aUser = setTestUser();
		Mockito.when(uDAO.findById(4200)).thenReturn(aUser);
		User returnValue = service.findById(4200);
		assert(returnValue.getUsername() == "Xargothrax");
		assert(returnValue.getPassword() == "universeOnFire");
	}
	
	@Test void findByUsernameTest() {
		Optional<User> aUser = setTestUser();
		Mockito.when(uDAO.findByUsername("Xargothrax")).thenReturn(aUser);
		User returnValue = service.findByUsername("Xargothrax");
		assert(returnValue.getUsername() == "Xargothrax");
		assert(returnValue.getPassword() == "universeOnFire");
	}
	
	@Test void insertTest() {
		Optional<User> aUser = setTestUser();
		Mockito.when(uDAO.save(aUser.get())).thenReturn(aUser.get());
		User returnValue = service.insert(aUser.get());
		assert(returnValue.getUsername() == "Xargothrax");
		assert(returnValue.getPassword() == "universeOnFire");
	}

	@Test void paySalaryTest() {
		Optional<User> aUser = setTestUser();
		Employee e = new Employee();
		e.setSalary(24000);
		aUser.get().setEmployee_data(e);
		
		CheckingsAccount anAccount = new CheckingsAccount();
		anAccount.setId(4200);
		anAccount.setBalance(234);
		
		List<CheckingsAccount> accountList = new ArrayList<CheckingsAccount>();
		accountList.add(anAccount);
				
		aUser.get().setCAccounts(accountList);
		
		aUser.get().getCAccounts().get(0).setBalance(aUser.get().getCAccounts().get(0).getBalance()+(aUser.get().getEmployee_data().getSalary()/24));
		
		assert(aUser.get().getCAccounts().get(0).getBalance() == 1234);
	}
	
	@Test void paySalaryTest2() {
		
		System.out.println("test system out.");
		
		Employee e = new Employee();
		e.setSalary(24000);
		
		CheckingsAccount anAccount = new CheckingsAccount();
		anAccount.setId(4200);
		anAccount.setBalance(234);
		List<CheckingsAccount> accountList = new ArrayList<CheckingsAccount>();
		accountList.add(anAccount);
		
		User aUser = setTestUser().get();
		aUser.setEmployee_data(e);
		aUser.setCAccounts(accountList);
		
		List<User> userSet = new ArrayList<User>();
		userSet.add(aUser);
		
		Mockito.when(uDAO.findAll()).thenReturn(userSet);
		Mockito.when(UserService.paySalary()).thenReturn(userSet);
		Set<User> returnValue = service.paySalary();
		List<User> returnList = new ArrayList<User>();
		returnValue.forEach(i -> returnList.add(i));
		for (User u : returnList) {
			System.out.println(u.getCAccounts().get(0).getBalance());
		}
		
		
		assert(returnList.get(0).getCAccounts().get(0).getBalance() == 1234);
	}
	
	private static Optional<User> setTestUser(){
		Optional<User> aUser;
		aUser = Optional.of(new User());
		aUser.get().setUsername("Xargothrax");
		aUser.get().setPassword("universeOnFire");	
		aUser.get().setId(4200);
		return aUser;
	}
}
