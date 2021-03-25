package com.revature.tests.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.revature.controllers.UserController;
import com.revature.models.User;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControlerTests {

	@Autowired private UserController controller;
	@Autowired private MockMvc aMockMvc;
	
	//@beforeAll void addCustomUser
	
	@Test void controllerExists() {
		assertThat(controller).isNotNull();
	}
	
	@Test void testFindAllURL() throws Exception{
		this.aMockMvc
				.perform(get("/users"))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	
	@Test void testFindById() throws Exception{
		this.aMockMvc
				.perform(get("users/13"))
				.andDo(print())
				.andExpect(content().string(containsString("\"id\":13")))
				.andExpect(status().isOk());
	}
	
//	@Test void testInsert() throws Exception{
//		int testId =  (int) Math.random() + 1000;
//		String jsonString = "{\"id\":\""+testId+"\",\"username\":\"testUsername"+testId	
//				+"\",\"password\":\"testPassword\",\"employee\":\"false\"}";
//		this.aMockMvc.perform(MockMvcRequestBuilders
//				.post("/").content(jsonString)
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON))
//				.andDo(print())
//				.andExpect(status().isOk());
//		//TODO: add delete user method so that I can test inserting this.aMockMvc;
//	}
//	@Test void testSalary() throws Exception {
//		this.aMockMvc
//				.perform(MockMvcRequestBuilders.post("/salary"))
//				.andDo(print())
//				.andExpect(status().isOk());
//	}
	
	 
	
	
//	@Test void () throws Exception{
//		this.aMockMvc
//			.perform(get("/users"))
//			.andDo(print())
//			.andExpect(status().isOk());
//	}
	
	
}
