package com.revature.services;

//import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.exception.JDBCConnectionException;
//import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;

import com.revature.models.Account;
import com.revature.models.CheckingsAccount;
import com.revature.models.SavingsAccount;
import com.revature.repositories.CheckingsAccountDAO;
import com.revature.repositories.SavingsAccountDAO;
import com.revature.repositories.UserDAO;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import com.revature.models.User;

@ExtendWith(MockitoExtension.class)
public class AccountServicesTest {

	@InjectMocks
	AccountServices services = new AccountServices(new SimpleMeterRegistry());

	@Mock
	SavingsAccountDAO savingsAccountDAO;
	@Mock
	CheckingsAccountDAO checkingsAccountDAO;
	@Mock
	UserDAO userDAO;

	@Test
	public void findCheckingsAccountsByIdTest() {

		CheckingsAccount ca = new CheckingsAccount();
		ca.setId(1);
		ca.setBalance(123);

		List<User> users = new ArrayList<>();
		User user = new User();
		user.setEmployee(false);
		user.setPassword("password");
		user.setUsername("user");

		users.add(user);
		ca.setU(users);

		when(checkingsAccountDAO.findById(1)).thenReturn(Optional.of(ca));

		// test
		CheckingsAccount cAccnt = services.findCheckingsAccountsById(1);

		assertEquals(1, cAccnt.getId());
		assertEquals(123, cAccnt.getBalance());
		assertFalse(cAccnt.getU().get(0).isEmployee());

	}

	@Test
	public void findAllCheckingsAccountsTest() {

		CheckingsAccount ca = new CheckingsAccount();
		ca.setId(1);
		ca.setBalance(123);

		List<User> users = new ArrayList<>();
		User user = new User();
		user.setEmployee(false);
		user.setPassword("password");
		user.setUsername("user");

		users.add(user);
		ca.setU(users);

		List<CheckingsAccount> Data = new ArrayList<CheckingsAccount>();
		Data.add(ca);
		when(checkingsAccountDAO.findAll()).thenReturn(Data);

		// test
		Set<CheckingsAccount> cAccnts = services.findAllCheckingsAccounts();
		assertEquals(1, cAccnts.size());

		CheckingsAccount cAccnt = cAccnts.iterator().next();
		assertEquals(1, cAccnt.getId());
		assertEquals(123, cAccnt.getBalance());
		assertFalse(cAccnt.getU().get(0).isEmployee());
	}
	
	@Test
	public void findAllCheckingsAccountsExceptionTest() {

		
		when(checkingsAccountDAO.findAll()).thenThrow(JDBCConnectionException.class);

		// test
		Set<CheckingsAccount> cAccnts = services.findAllCheckingsAccounts();
		assertNull(cAccnts);
	}


	@Test
	public void insertCheckingAccountTest() {
		User user = new User();
		user.setEmployee(false);
		user.setPassword("password");
		user.setUsername("user");
		when(userDAO.findById(1)).thenReturn(Optional.of(user));
		when(userDAO.save(user)).thenReturn((user));

		CheckingsAccount ca = new CheckingsAccount();
		ca.setId(1);
		ca.setBalance(123);
		when(checkingsAccountDAO.save(ca)).thenReturn(ca);

		// Test
		CheckingsAccount testAcct = services.insert(ca, 1);
		assertEquals(1, testAcct.getId());
		assertEquals(123, testAcct.getBalance());
		assertFalse(testAcct.getU().get(0).isEmployee());
	}

	@Test
	public void insertSavingsAccountTest() {
		User user = new User();
		user.setEmployee(false);
		user.setPassword("password");
		user.setUsername("user");
		when(userDAO.findById(1)).thenReturn(Optional.of(user));
		when(userDAO.save(user)).thenReturn((user));

		SavingsAccount sa = new SavingsAccount();
		sa.setId(1);
		sa.setBalance(123);
		when(savingsAccountDAO.save(sa)).thenReturn(sa);

		// Test
		SavingsAccount testAcct = services.insert(sa, 1);
		assertEquals(1, testAcct.getId());
		assertEquals(123, testAcct.getBalance());
		assertFalse(testAcct.getU().get(0).isEmployee());
	}

	@Test
	public void findAllSavingsAccountsTest() {

		SavingsAccount sa = new SavingsAccount();
		sa.setId(1);
		sa.setBalance(123);

		List<User> users = new ArrayList<>();
		User user = new User();
		user.setEmployee(false);
		user.setPassword("password");
		user.setUsername("user");

		users.add(user);
		sa.setU(users);

		List<SavingsAccount> Data = new ArrayList<SavingsAccount>();
		Data.add(sa);
		when(savingsAccountDAO.findAll()).thenReturn(Data);

		// test
		Set<SavingsAccount> sAccnts = services.findAllSavingsAccounts();
		assertEquals(1, sAccnts.size());

		SavingsAccount sAccnt = sAccnts.iterator().next();
		assertEquals(1, sAccnt.getId());
		assertEquals(123, sAccnt.getBalance());
		assertFalse(sAccnt.getU().get(0).isEmployee());
	}

	@Test
	public void findSavingsAccountsByIdTest() {

		SavingsAccount sa = new SavingsAccount();
		sa.setId(1);
		sa.setBalance(123);

		List<User> users = new ArrayList<>();
		User user = new User();
		user.setEmployee(false);
		user.setPassword("password");
		user.setUsername("user");

		users.add(user);
		sa.setU(users);

		when(savingsAccountDAO.findById(1)).thenReturn(Optional.of(sa));

		// test
		SavingsAccount sAccnt = services.findSavingsAccountsById(1);

		assertEquals(1, sAccnt.getId());
		assertEquals(123, sAccnt.getBalance());
		assertFalse(sAccnt.getU().get(0).isEmployee());

	}

	@Test
	public void transfer() {

		SavingsAccount sa = new SavingsAccount();
		sa.setId(1);
		sa.setBalance(100);

		List<SavingsAccount> Data = new ArrayList<SavingsAccount>();
		Data.add(sa);
		when(savingsAccountDAO.findAll()).thenReturn(Data);
		when(savingsAccountDAO.save(sa)).thenReturn(sa);
		CheckingsAccount ca = new CheckingsAccount();
		ca.setId(2);
		ca.setBalance(10);

		List<CheckingsAccount> Data2 = new ArrayList<CheckingsAccount>();
		Data2.add(ca);
		when(checkingsAccountDAO.findAll()).thenReturn(Data2);
		when(checkingsAccountDAO.save(ca)).thenReturn(ca);
		//test
		Set<Account> postTransfer = services.transfer(1, 2, 50);
		Iterator<Account> iterator = postTransfer.iterator();
		Account checking = iterator.next();
		Account saving = iterator.next();
		
		
		assertEquals(1, saving.getId());
		assertEquals(50, saving.getBalance());
		
		assertEquals(2, checking.getId());
		assertEquals(60, checking.getBalance());
			
	}

	@Test
	public void grantAccessToUser() {
		SavingsAccount sa = new SavingsAccount();
		sa.setId(1);
		sa.setBalance(100);

		List<SavingsAccount> Data = new ArrayList<SavingsAccount>();
		Data.add(sa);
		when(savingsAccountDAO.findAll()).thenReturn(Data);
		when(savingsAccountDAO.save(sa)).thenReturn(sa);
		CheckingsAccount ca = new CheckingsAccount();
		ca.setId(2);
		ca.setBalance(10);

		List<CheckingsAccount> Data2 = new ArrayList<CheckingsAccount>();
		Data2.add(ca);
		when(checkingsAccountDAO.findAll()).thenReturn(Data2);
	
		User user = new User();
		user.setEmployee(false);
		user.setPassword("password");
		user.setUsername("user");
		when(userDAO.findById(1)).thenReturn(Optional.of(user));
		
		//test
		assertNull(sa.getU());
		services.grantAccessToUser(1, 1);
		assertEquals(1,sa.getU().size());
		
	}

}
