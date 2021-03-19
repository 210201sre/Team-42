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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.User;
import com.revature.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	/*fields*/
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired private UserService userService;

	/*GET methods*/
	@GetMapping
	public ResponseEntity<Set<User>> findAll() {
		MDC.put("event", "find all users");
		
		Set<User> allUsers = userService.findAll();
		if (allUsers.isEmpty()) {
			log.warn("no users found :(");
			MDC.clear();
			return ResponseEntity.noContent().build();
		}
		log.info("users found");
		MDC.clear();
		return ResponseEntity.ok(allUsers);

	}

	@GetMapping("/{userid}")
	public ResponseEntity<User> findByUsername(@PathVariable("userid") int id) {
		MDC.put("event", "find all user by id");
		
		User user = userService.findById(id);
		
		MDC.put("user id", user.getId());
		log.info("users found");
		MDC.clear();
		
		return ResponseEntity.ok(user);
	}

	
	/*POST methods*/

	
	@PostMapping
	public ResponseEntity<User> insert(@Valid @RequestBody User u) {
		return ResponseEntity.ok(userService.insert(u));
	}

	@PostMapping("/salary")
	public ResponseEntity<Set<User>> salary() {

		Set<User> allUsers = userService.paySalary();

		if (allUsers.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(allUsers);
	}
}
