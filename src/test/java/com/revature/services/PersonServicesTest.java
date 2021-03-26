package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.User;
import com.revature.repositories.CustomerDAO;
import com.revature.repositories.EmployeeDAO;
import com.revature.repositories.UserDAO;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

@ExtendWith(MockitoExtension.class)
public class PersonServicesTest {
	
	

		@InjectMocks
		PersonServices services = new PersonServices(new SimpleMeterRegistry());;

		@Mock
		private EmployeeDAO employeeDAO;
		@Mock
		private CustomerDAO customerDAO;
		@Mock
		private UserDAO userDAO;

		@Test
		public void findEmployeeByIdTest() {

			Employee emp = new Employee();
			emp.setId(1);
			emp.setFirstName("Jessica");
			emp.setLastName("Parker");
			when(employeeDAO.findById(1)).thenReturn(Optional.of(emp));
			// test
			Employee temp = services.findEmployeeById(1);

			assertEquals(1, temp.getId());
			assertEquals("Jessica", temp.getFirstName());
			assertEquals("Parker", temp.getLastName());

		}

		@Test
		public void findAllEmployeesTest() {

			Employee emp = new Employee();
			emp.setId(1);
			emp.setFirstName("Jessica");
			emp.setLastName("Parker");
			
			Employee emp2 = new Employee();
			emp2.setId(2);
			emp2.setFirstName("Bob");
			emp2.setLastName("Jones");
			List<Employee> empList = new ArrayList<>();
			empList.add(emp);
			empList.add(emp2);
			when(employeeDAO.findAll()).thenReturn(empList);
			
			// test
			Set<Employee> temp = services.findAllEmployees();

			Iterator<Employee> iterator = temp.iterator();
			Employee tempEmp2 = iterator.next();
			Employee tempEmp = iterator.next();
			
			assertEquals(1, tempEmp.getId());
			assertEquals("Jessica", tempEmp.getFirstName());
			assertEquals("Parker", tempEmp.getLastName());
			
			assertEquals(2, tempEmp2.getId());
			assertEquals("Bob", tempEmp2.getFirstName());
			assertEquals("Jones", tempEmp2.getLastName());
			
		}

		@Test
		public void insertEmployeeTest() {
			User user = new User();
			user.setEmployee(false);
			user.setPassword("password");
			user.setUsername("user");
			when(userDAO.findById(1)).thenReturn(Optional.of(user));
			when(userDAO.save(user)).thenReturn((user));

			Employee emp = new Employee();
			emp.setId(1);
			emp.setFirstName("Jessica");
			emp.setLastName("Parker");
			when(employeeDAO.save(emp)).thenReturn(emp); 
			

			// Test
			Employee temp = services.insert(emp, 1);
			assertEquals(1, temp.getId());
			assertEquals("Jessica", temp.getFirstName());
			assertEquals("Parker", temp.getLastName());
			
			assertTrue(user.isEmployee());
			
		}

		@Test
		public void findCustomerByIdTest() {

			Customer c = new Customer();
			c.setId(1);
			c.setFirstName("Jessica");
			c.setLastName("Parker");
			when(customerDAO.findById(1)).thenReturn(Optional.of(c));
			// test
			Customer temp = services.findCustomerById(1);

			assertEquals(1, temp.getId());
			assertEquals("Jessica", temp.getFirstName());
			assertEquals("Parker", temp.getLastName());

		}

		@Test
		public void findAllCustomersTest() {

			Customer c = new Customer();
			c.setId(1);
			c.setFirstName("Jessica");
			c.setLastName("Parker");
			
			Customer c2 = new Customer();
			c2.setId(2);
			c2.setFirstName("Bob");
			c2.setLastName("Jones");
			List<Customer> customerList = new ArrayList<>();
			customerList.add(c);
			customerList.add(c2);
			when(customerDAO.findAll()).thenReturn(customerList);
			
			// test
			Set<Customer> temp = services.findAllCustomers();

			Iterator<Customer> iterator = temp.iterator();
			Customer tempC2 = iterator.next();
			Customer tempC = iterator.next();
			
			assertEquals(1, tempC.getId());
			assertEquals("Jessica", tempC.getFirstName());
			assertEquals("Parker", tempC.getLastName());
			
			assertEquals(2, tempC2.getId());
			assertEquals("Bob", tempC2.getFirstName());
			assertEquals("Jones", tempC2.getLastName());
		
		}

		@Test
		public void insertCustomerTest() {
			User user = new User();
			user.setEmployee(false);
			user.setPassword("password");
			user.setUsername("user");
			when(userDAO.findById(1)).thenReturn(Optional.of(user));
			when(userDAO.save(user)).thenReturn((user));

			Customer c = new Customer();
			c.setId(1);
			c.setFirstName("Jessica");
			c.setLastName("Parker");
			when(customerDAO.save(c)).thenReturn(c); 
			
			// Test
			Customer temp = services.insert(c, 1);
			assertEquals(1, temp.getId());
			assertEquals("Jessica", temp.getFirstName());
			assertEquals("Parker", temp.getLastName());
			
		}

			
		

	
}
