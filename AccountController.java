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

import com.revature.models.SavingsAccount;
import com.revature.models.Account;
import com.revature.models.CheckingsAccount;
import com.revature.models.User;
import com.revature.services.AccountServices;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
public class AccountController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	private MeterRegistry meterRegistry;
	private Counter successCounter;
	
	public AccountController (MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
        successCounter = meterRegistry.counter("accessed account", "type", "success");
	}
	
	@Autowired
	private AccountServices accountServices;

	@GetMapping("/checkings_accounts")
	public ResponseEntity<Set<CheckingsAccount>> findAllCheckings() {
		MDC.put("event", "find all checkings account");
		Set<CheckingsAccount> allCAcounts = accountServices.findAllCheckingsAccounts();

		if (allCAcounts.isEmpty()) {
			log.warn("accounts not found");
			MDC.clear();
			return ResponseEntity.noContent().build();
		}
		log.info("accounts found");
		MDC.clear();
		return ResponseEntity.ok(allCAcounts);
	}

	@PostMapping("/checkings_accounts/{id}")
	public ResponseEntity<CheckingsAccount> insert(@Valid @RequestBody CheckingsAccount cA,
			@PathVariable("id") int id) {
		return ResponseEntity.ok(accountServices.insert(cA, id));

	}

	@GetMapping("/checkings_accounts/{id}")
	public ResponseEntity<CheckingsAccount> findCheckingsAccountsById(@PathVariable("id") int id) {
		MDC.put("event", "find checkings account by id");
		MDC.put("accountid", id);
		CheckingsAccount account = accountServices.findCheckingsAccountsById(id);
		if (account == null)
			log.warn("account not found");
		log.info("account found");
		MDC.clear();
		return ResponseEntity.ok(account);
	}

	@GetMapping("/savings_accounts/{id}")
	public ResponseEntity<SavingsAccount> findSavingsAccountsById(@PathVariable("id") int id) {
		MDC.put("event", "find savings account by id");
		MDC.put("accountid", id);

		SavingsAccount account = accountServices.findSavingsAccountsById(id);
		if (account == null)
			log.warn("account not found");
		log.info("account found");
		MDC.clear();
		return ResponseEntity.ok(account);
	}

	@GetMapping("/savings_accounts")
	public ResponseEntity<Set<SavingsAccount>> findAllSavings() {
		MDC.put("event", "find all savings account");
		Set<SavingsAccount> allSAcounts = accountServices.findAllSavingsAccounts();

		if (allSAcounts.isEmpty()) {
			log.warn("accounts not found");
			MDC.clear();
			return ResponseEntity.noContent().build();
		}
		log.info("accounts found");
		MDC.clear();
		return ResponseEntity.ok(allSAcounts);
	}

	@PostMapping("/savings_accounts/{id}")
	public ResponseEntity<SavingsAccount> insert(@Valid @RequestBody SavingsAccount sA, @PathVariable("id") int id) {
		return ResponseEntity.ok(accountServices.insert(sA, id));

	}

	@PostMapping("/accounts/transfer/{id1}/{id2}/{amount}")
	public ResponseEntity<Set<Account>> transfer(@PathVariable("id1") int id1, @PathVariable("id2") int id2,
			@PathVariable("amount") double amount) {
		Set<Account> allAcounts = accountServices.transfer(id1, id2, amount);

		if (allAcounts.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(allAcounts);

	}

	@PostMapping("/accounts/acess/{actid}/{userid}")
	public ResponseEntity<User> grantAccess(@PathVariable("actid") int actId, @PathVariable("userid") int userId) {
		User u = accountServices.grantAccessToUser(actId, userId);
		successCounter.increment(1);
		return ResponseEntity.ok(u);

	}

}