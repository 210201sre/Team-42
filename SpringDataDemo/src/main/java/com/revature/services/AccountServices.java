package com.revature.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import com.revature.models.Account;
import com.revature.models.CheckingsAccount;
import com.revature.models.Customer;
import com.revature.models.SavingsAccount;
import com.revature.models.User;
import com.revature.repositories.CheckingsAccountDAO;
import com.revature.repositories.SavingsAccountDAO;
import com.revature.repositories.UserDAO;

@Service
public class AccountServices {

	/*fields*/
	private static final Logger log=LoggerFactory.getLogger(UserController.class);
	
	@Autowired private SavingsAccountDAO savingsAccountDAO;
	@Autowired private CheckingsAccountDAO checkingsAccountDAO;
	@Autowired private UserDAO userDAO;

	/*Create methods*/
	public CheckingsAccount insert(CheckingsAccount e, int id) {

		MDC.put("event", "make account");
		MDC.put("userid", id);
		MDC.put("accountid", e.getId());
		
		Optional<User> anOptionalUser = userDAO.findById(id);
		User u = anOptionalUser.get();
		if(e.getU() == null){
			List<User> users = new ArrayList<>();
			users.add(u);
			e.setU(users);
		}
		else e.getU().add(u);
		CheckingsAccount cATemp = checkingsAccountDAO.save(e);
		MDC.put("accountid", e.getId());
		u.getCAccounts().add(cATemp);
		log.info("checkings account created and linked to user");
		userDAO.save(u);
		MDC.clear();
		return cATemp;
	}
	public SavingsAccount insert(SavingsAccount e, int id) {
		Optional<User> oU = userDAO.findById(id);
		User u = oU.get();
		MDC.put("event", "make account");
		MDC.put("userid", id);
		MDC.put("accountid", e.getId());
		if(e.getU( )== null)
		{
			List<User> users=new ArrayList<>();
			users.add(u);
			e.setU(users);
			
		}
		else e.getU().add(u);
		SavingsAccount cATemp = savingsAccountDAO.save(e);

		u.getSAccounts().add(cATemp);
		userDAO.save(u);
		log.info("checkings account created and linked to user");
		MDC.clear();
		return cATemp;
	}
	
	/*Read methods*/
	public Set<CheckingsAccount> findAllCheckingsAccounts() {
		return checkingsAccountDAO.findAll().stream().collect(Collectors.toSet());
	}

	public CheckingsAccount findCheckingsAccountsById(int id) {
		return checkingsAccountDAO.findById(id).orElseThrow(() -> 
				new UserNotFoundException("No Employee found with id " + id));
	}

	public Set<SavingsAccount> findAllSavingsAccounts() {
		MDC.put("event", "make account");
		log.info("found all savings accounts");
		MDC.clear();
		return savingsAccountDAO.findAll().stream().collect(Collectors.toSet());
	}

	public SavingsAccount findSavingssAccountsById(int id) {
		MDC.put("event", "find account by id");
		MDC.put("Account Id", id);
		log.info("found savings account with id");
		MDC.clear();
		return savingsAccountDAO.findById(id).orElseThrow(() -> new UserNotFoundException("No Employee found with id " + id));
	}

	/*Update methods*/
	public Set<Account> transfer(int id1, int id2, double amount) {
		Account account1 = null;
		Account account2 = null;
		
		MDC.put("event", "Grant Acess");
		MDC.put("Account_Id_1", id1);
		MDC.put("Account_id_2", id2);
		MDC.put("Amount", amount);
		
		Set<SavingsAccount> tempSAccounts = new HashSet<>();
		tempSAccounts = savingsAccountDAO.findAll().stream().collect(Collectors.toSet());
		Set<CheckingsAccount> tempCAccounts = new HashSet<>();
		tempCAccounts = checkingsAccountDAO.findAll().stream().collect(Collectors.toSet());
		Set<Account> accounts = new HashSet<>();
		accounts.addAll(tempCAccounts);
		accounts.addAll(tempSAccounts);
		
		for(Account account : accounts){
			if(account.getId()==id1) {
				account1 = account;
			}
			if(account.getId()==id2) {
				account2 = account;
			}
		}
		if(account1 == null || account2 == null){
			log.warn("account not found");
			MDC.clear();
			return null;
		}
		account1.setBalance(account1.getBalance()-amount);
		account2.setBalance(account2.getBalance()+amount);

		if(account1 instanceof SavingsAccount) {
			savingsAccountDAO.save((SavingsAccount) account1);
		} else {
			checkingsAccountDAO.save((CheckingsAccount) account1);
		}
		if(account2 instanceof SavingsAccount) {
			savingsAccountDAO.save((SavingsAccount) account2);
		} else {
			checkingsAccountDAO.save((CheckingsAccount) account2);
		}
		Set<Account> returnaccounts = new HashSet<>();
		returnaccounts.add(account1);
		returnaccounts.add(account2);
		
		log.info("founds transfered");
		MDC.clear();
		return returnaccounts;
	}

	/*Delete methods*/

	/*other methods*/
	public User grantAccessToUser(int actId,int userId) {
		MDC.put("event", "Grant Acess");
		MDC.put("Account Id", actId);
		MDC.put("User Id", userId);
		
		User u = userDAO.findById(userId).get();
		Account account1 = null;
		Set<SavingsAccount> tempSAccounts = new HashSet<>();
		tempSAccounts = savingsAccountDAO.findAll().stream().collect(Collectors.toSet());
		Set<CheckingsAccount> tempCAccounts = new HashSet<>();
		tempCAccounts = checkingsAccountDAO.findAll().stream().collect(Collectors.toSet());
		Set<Account> accounts = new HashSet<>();
		accounts.addAll(tempCAccounts);
		accounts.addAll(tempSAccounts);
		
		for(Account account : accounts){
			if(account.getId() == actId) {
				account1=account;
			}
		}
		
		if(account1 == null) {
			log.warn("account not found");
			MDC.clear();
			return null;
		}
		
		if(account1 instanceof SavingsAccount) {
			u.getSAccounts().add((SavingsAccount)account1);
			((SavingsAccount) account1).getU().add(u);
			savingsAccountDAO.save((SavingsAccount)account1);
		} else {
			u.getCAccounts().add((CheckingsAccount)account1);
			((CheckingsAccount) account1).getU().add(u);
			checkingsAccountDAO.save((CheckingsAccount)account1);
		}
		log.info("access granted");
		return u;
	}
}
