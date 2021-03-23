package com.revature;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.controllers.AccountController;

@SpringBootTest
class SpringDataDemoApplicationTests {
@Autowired
public AccountController acctController; 
	@Test
	void contextLoads() throws Exception {
		assertThat(acctController).isNotNull();
	}

}
