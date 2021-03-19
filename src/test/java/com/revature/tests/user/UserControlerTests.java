package com.revature.tests.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.controllers.UserController;

@SpringBootTest
public class UserControlerTests {

	@Autowired private UserController controller;
	
	@Test private void controllerExists() {
		assertThat(controller).isNotNull();
	}
	
	
}
