

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
		int anId = 4200;
		Optional<User> aUser;
		aUser = Optional.of(new User());
		aUser.get().setUsername("Xargothrax");
		aUser.get().setPassword("universeOnFire");	
		aUser.get().setId(anId);
		List<User> resultList = new ArrayList<User>();
		resultList.add(aUser.get());
		Mockito.when(uDAO.findAll()).thenReturn(resultList);
	
		Set<User> resultSet = service.findAll();
		
		assert(!resultSet.isEmpty());
	}
	
	@Test void findByIdTest() {
		int anId = 4200;
		Optional<User> aUser;
		aUser = Optional.of(new User());
		aUser.get().setUsername("Xargothrax");
		aUser.get().setPassword("universeOnFire");	
		aUser.get().setId(anId);
		Mockito.when(uDAO.findById(anId)).thenReturn(aUser);
		User returnValue = service.findById(4200);
		assert(returnValue.getUsername() == "Xargothrax");
		assert(returnValue.getPassword() == "universeOnFire");
	}
	
	@Test void findByUsernameTest() {
		int anId = 4200;
		String theUsername ="Xargothrax";
		Optional<User> aUser;
		aUser = Optional.of(new User());
		aUser.get().setUsername(theUsername);
		aUser.get().setPassword("universeOnFire");	
		aUser.get().setId(anId);
		Mockito.when(uDAO.findByUsername(theUsername)).thenReturn(aUser);
		User returnValue = service.findByUsername(theUsername);
		assert(returnValue.getUsername() == "Xargothrax");
		assert(returnValue.getPassword() == "universeOnFire");
	}
	
	@Test void insertTest() {
		int anId = 4200;
		String theUsername ="Xargothrax";
		Optional<User> aUser;
		aUser = Optional.of(new User());
		aUser.get().setUsername(theUsername);
		aUser.get().setPassword("universeOnFire");	
		aUser.get().setId(anId);
		Mockito.when(uDAO.save(aUser.get())).thenReturn(aUser.get());
		User returnValue = service.insert(aUser.get());
		assert(returnValue.getUsername() == "Xargothrax");
		assert(returnValue.getPassword() == "universeOnFire");
	}

//	@Test void paySalaryTest() {
//		int anId = 4200;
//		Optional<User> aUser;
//		aUser = Optional.of(new User());
//		aUser.get().setUsername("Xargothrax");
//		aUser.get().setPassword("universeOnFire");	
//		aUser.get().setId(anId);
//		
//		Employee e = new Employee();
//		e.setSalary(4000);
//		
//		aUser.get().setEmployee_data(e);
//		
//		CheckingsAccount anAccount = new CheckingsAccount();
//		anAccount.setId(anId);
//		anAccount.setBalance(234);
//		List<CheckingsAccount> accountList = new ArrayList<CheckingsAccount>();
//		accountList.add(anAccount);
//				
//		aUser.get().setCAccounts(accountList);
//		
//		List<User> resultList = new ArrayList<User>();
//		resultList.add(aUser.get());
//		
//		Set<User> returnValue = service.paySalary();
//		assert(returnValue.getCAccounts().get(0).getBalance() == 1234);
//	}
}
