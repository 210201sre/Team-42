package com.revature.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import com.revature.models.Account;
import com.revature.models.CheckingsAccount;
//import com.revature.models.Customer;
import com.revature.models.SavingsAccount;
import com.revature.models.User;
import com.revature.repositories.CheckingsAccountDAO;
import com.revature.repositories.SavingsAccountDAO;
import com.revature.repositories.UserDAO;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Service
public class AccountServices {
	private MeterRegistry meterRegistry;
	private Counter successAccessCounter;
	private Counter accessCounter;
	private Counter successJdbcCounter;
	private Counter jdbcCounter;
	
	public AccountServices (MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
        successAccessCounter = meterRegistry.counter("accessed account", "type", "success");
        accessCounter = meterRegistry.counter("accessed account", "type", "access");
        successJdbcCounter = meterRegistry.counter("jdbc connected", "type", "success");;
    	jdbcCounter = meterRegistry.counter("jdbc connected", "type", "connect");;
	}

	@Autowired
	private SavingsAccountDAO savingsAccountDAO;
	@Autowired
	private CheckingsAccountDAO checkingsAccountDAO;
	@Autowired
	private UserDAO userDAO;

	private static final Logger log=LoggerFactory.getLogger(UserController.class);
	
	public Set<CheckingsAccount> findAllCheckingsAccounts() {
		Set<CheckingsAccount> caSet = null;
		jdbcCounter.increment(1);
		try {
			caSet = checkingsAccountDAO.findAll().stream().collect(Collectors.toSet());
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException e) {
			log.info(e.toString());
		}
		return caSet;
	}

	public CheckingsAccount findCheckingsAccountsById(int id) {
		CheckingsAccount ca = null;
		jdbcCounter.increment(1);
		try {
			ca = checkingsAccountDAO.findById(id).orElseThrow(() -> new UserNotFoundException("No Employee found with id " + id));
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException e) {
			log.info(e.toString());
		}
		return ca;
	}

	public CheckingsAccount insert(CheckingsAccount e, int id) {
		Optional<User> oU = Optional.empty();// change null to optional to prevent error in the get call 
		jdbcCounter.increment(1);
		try {
			oU = userDAO.findById(id);
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		User u = oU.get();
		MDC.put("event", "make account");
		MDC.put("userid", id);
		MDC.put("accountid", e.getId());
		if(e.getU()==null)
		{
			List<User> users=new ArrayList<>();
			users.add(u);
			e.setU(users);
			
		}
		else e.getU().add(u);
		CheckingsAccount cATemp = null;
		jdbcCounter.increment(1);
		try {
			cATemp = checkingsAccountDAO.save(e);
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		MDC.put("accountid", e.getId());
		//u.getCAccounts().add(cATemp);   
		if(u.getCAccounts()==null) //safety check to ensure user has checking account
		{
			List<CheckingsAccount> cAccts=new ArrayList<>();
			cAccts.add(cATemp);
			u.setCAccounts(cAccts);
			
		}
		else u.getCAccounts().add(cATemp);
		// end of add
		
		log.info("checkings account created and linked to user");
		jdbcCounter.increment(1);
		try {
			userDAO.save(u);
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		MDC.clear();
		return cATemp;
	}
	
	public Set<SavingsAccount> findAllSavingsAccounts() {
		MDC.put("event", "make account");
		log.info("found all savings accounts");
		MDC.clear();
		Set<SavingsAccount> saSet = null;
		jdbcCounter.increment(1);
		try {
			saSet = savingsAccountDAO.findAll().stream().collect(Collectors.toSet());
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		return saSet;
	}

	public SavingsAccount findSavingsAccountsById(int id) {
		MDC.put("event", "find account by id");
		MDC.put("Account Id", id);
		log.info("found savings account with id");
		MDC.clear();
		SavingsAccount sa = null;
		jdbcCounter.increment(1);
		try {
			sa = savingsAccountDAO.findById(id).orElseThrow(() -> new UserNotFoundException("No Employee found with id " + id));
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		return sa;
	}

	public SavingsAccount insert(SavingsAccount e, int id) {
		Optional<User> oU = Optional.empty();//change null to optional to prevent error in the get call
		jdbcCounter.increment(1);
		try {
			oU = userDAO.findById(id);
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		User u = oU.get();
		MDC.put("event", "make account");
		MDC.put("userid", id);
		MDC.put("accountid", e.getId());
		if(e.getU()==null)
		{
			List<User> users=new ArrayList<>();
			users.add(u);
			e.setU(users);
			
		}
		else e.getU().add(u);
		SavingsAccount cATemp = null;
		jdbcCounter.increment(1);
		try {
			cATemp = savingsAccountDAO.save(e);
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		
		//u.getSAccounts().add(cATemp);   
				if(u.getSAccounts()==null) //safety check to ensure user has a savings account
				{
					List<SavingsAccount> SAccts=new ArrayList<>();
					SAccts.add(cATemp);
					u.setSAccounts(SAccts);
					
				}
				else u.getSAccounts().add(cATemp);
				// end of add
		jdbcCounter.increment(1);
		try {
			userDAO.save(u);
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		log.info("checkings account created and linked to user");
		MDC.clear();
		return cATemp;
	}

	public Set<Account> transfer(int id1, int id2, double amount) {
		Account account1=null;
		Account account2=null;
		
		MDC.put("event", "Grant Access");
		MDC.put("Account_Id_1", id1);
		MDC.put("Account_id_2", id2);
		MDC.put("Amount", amount);
		
		Set<SavingsAccount> tempSAccounts=new HashSet<>();
		jdbcCounter.increment(1);
		try {
			tempSAccounts=savingsAccountDAO.findAll().stream().collect(Collectors.toSet());
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		Set<CheckingsAccount> tempCAccounts=new HashSet<>();
		jdbcCounter.increment(1);
		try {
			tempCAccounts=checkingsAccountDAO.findAll().stream().collect(Collectors.toSet());
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		Set<Account> accounts=new HashSet<>();
		accounts.addAll(tempCAccounts);
		accounts.addAll(tempSAccounts);
		
		for(Account account : accounts)
		{
			if(account.getId()==id1)
				account1=account;
			if(account.getId()==id2)
				account2=account;
		}
		if(account1==null || account2==null)
		{
			log.warn("account not found");
			MDC.clear();
			return null;
		}
		account1.setBalance(account1.getBalance()-amount);
		account2.setBalance(account2.getBalance()+amount);

		if(account1 instanceof SavingsAccount) {
			jdbcCounter.increment(1);
			try {
				savingsAccountDAO.save((SavingsAccount) account1);
				successJdbcCounter.increment(1);
			}
			catch (JDBCConnectionException j) {
				log.info(j.toString());
			}
		}
		else {
			jdbcCounter.increment(1);
			try {
				checkingsAccountDAO.save((CheckingsAccount) account1);
				successJdbcCounter.increment(1);
			}
			catch (JDBCConnectionException j) {
				log.info(j.toString());
			}
		}
		if(account2 instanceof SavingsAccount) {
			jdbcCounter.increment(1);
			try {
				savingsAccountDAO.save((SavingsAccount) account2);
				successJdbcCounter.increment(1);
			}
			catch (JDBCConnectionException j) {
				log.info(j.toString());
			}
		}
		else {
			jdbcCounter.increment(1);
			try {
				checkingsAccountDAO.save((CheckingsAccount) account2);
				successJdbcCounter.increment(1);
			}
			catch (JDBCConnectionException j) {
				log.info(j.toString());
			}
		}
		
		Set<Account> returnaccounts=new HashSet<>();
		returnaccounts.add(account1);
		returnaccounts.add(account2);
		
		log.info("founds transfered");
		MDC.clear();
		return returnaccounts;
		
	}
	
	public User grantAccessToUser(int actId,int userId) {
		accessCounter.increment(1);
		MDC.put("event", "Grant Access");
		MDC.put("Account Id", actId);
		MDC.put("User Id", userId);
		User u=userDAO.findById(userId).get();
		Account account1=null;
		Set<SavingsAccount> tempSAccounts=new HashSet<>();
		jdbcCounter.increment(1);
		try {
			tempSAccounts=savingsAccountDAO.findAll().stream().collect(Collectors.toSet());
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		Set<CheckingsAccount> tempCAccounts=new HashSet<>();
		jdbcCounter.increment(1);
		try {
			tempCAccounts=checkingsAccountDAO.findAll().stream().collect(Collectors.toSet());
			successJdbcCounter.increment(1);
		}
		catch (JDBCConnectionException j) {
			log.info(j.toString());
		}
		Set<Account> accounts=new HashSet<>();
		accounts.addAll(tempCAccounts);
		accounts.addAll(tempSAccounts);
		
		for(Account account : accounts)
		{
			if(account.getId()==actId)
				account1=account;
		}
		
		if(account1==null || u==null) {
			log.warn("account not found");
			MDC.clear();
			return null;
		}
		
		if(account1 instanceof SavingsAccount) {
			//u.getSAccounts().add((SavingsAccount)account1); 
			if(u.getSAccounts()==null) //safety check to ensure user has a savings account
			{
				List<SavingsAccount> accts=new ArrayList<>(); 
				accts.add((SavingsAccount)account1); 
				u.setSAccounts(accts);
				
			}
			else u.getSAccounts().add((SavingsAccount)account1);
			//((SavingsAccount) account1).getU().add(u); 
			
			if(((SavingsAccount)account1).getU()==null) //safety check to ensure user list exists
			{
				List<User> users=new ArrayList<>();
				users.add(u);
				((SavingsAccount)account1).setU(users);
				
			}
			else ((SavingsAccount)account1).getU().add(u);
			jdbcCounter.increment(1);
			try {
				savingsAccountDAO.save((SavingsAccount)account1);
				successJdbcCounter.increment(1);
			}
			catch (JDBCConnectionException j) {
				log.info(j.toString());
			}
		}
		else {
			//u.getCAccounts().add((CheckingsAccount)account1); 
			if(u.getCAccounts()==null)                 //safety check to ensure user has a checking account
			{
				List<CheckingsAccount> accts=new ArrayList<>();
				accts.add((CheckingsAccount)account1);
				u.setCAccounts(accts);
				
			}
			else u.getCAccounts().add((CheckingsAccount)account1);
			//((CheckingsAccount) account1).getU().add(u);  
			if(((CheckingsAccount)account1).getU()==null) //safety check to ensure checking account has a user list
			{
				List<User> users=new ArrayList<>();
				users.add(u);
				((CheckingsAccount)account1).setU(users);
				
			}
			else ((CheckingsAccount)account1).getU().add(u);
			jdbcCounter.increment(1);
			try {
				checkingsAccountDAO.save((CheckingsAccount)account1);
				successJdbcCounter.increment(1);
			}
			catch (JDBCConnectionException j) {
				log.info(j.toString());
			}
		
		}
		log.info("access granted");
		successAccessCounter.increment(1);
		return u;
		
	}

}
