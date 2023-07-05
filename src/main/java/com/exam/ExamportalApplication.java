package com.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.exam.service.UserService;

@SpringBootApplication
public class ExamportalApplication implements CommandLineRunner {
	
	@Autowired
	private UserService userService;
	

	public static void main(String[] args) {
		SpringApplication.run(ExamportalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
//		User user = new User();
//		user.setFirstName("Anubrata");
//		user.setLastName("Das");
//		user.setPassword("0101");
//		user.setEmail("anubrata450@gmail.com");
//		user.setUsername("ANUBRATA");
//		
//		Role role = new Role();
//		role.setRoleId(20L);
//		role.setRoleName("ADMIN");
//		
//		Set<UserRole> userRoleSet = new HashSet<>();		
//		UserRole userRole = new UserRole();
//		userRole.setRole(role);
//		userRole.setUser(user);
//		
//		userRoleSet.add(userRole);
//		
//		User user2 = this.userService.createUser(user, userRoleSet);
//		System.out.println(user2.getUsername());
//		
	}

}
