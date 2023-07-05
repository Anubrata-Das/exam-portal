package com.exam.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.config.JwtUtil;
import com.exam.model.JwtRequest;
import com.exam.model.JwtResponse;
import com.exam.model.User;


@RestController
@CrossOrigin("*")
public class AuthenticateController {
	
	private Logger logger = LoggerFactory.getLogger(AuthenticateController.class);
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
//	@Autowired
//	private UserServiceImpl userService;
	
	
	//generate token
	@PostMapping("/generate-token")
	public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest request){
		this.doAuthenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		
		String token = this.jwtUtil.generateToken(userDetails);
		
		JwtResponse response = JwtResponse.builder().jwtToken(token).username(userDetails.getUsername())
				.build();
		
		return new ResponseEntity<>(response, HttpStatus.OK);
		
		
	}
//	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{
//		try {
//			
//			authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
//			
//		} catch (UsernameNotFoundException e) {
//			throw new Exception("User not found");
//		}
//		
//		//authenticate
//		
//		UserDetails userDetails=this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
//		
//		String token = this.jwtUtil.generateToken(userDetails);
//		
//		return ResponseEntity.ok(new JwtResponse(token));
//	}
	
	private void doAuthenticate(String username, String password) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
		
		try {
			manager.authenticate(authentication);
			
		} catch (BadCredentialsException e) {
			// TODO: handle exception
			throw new BadCredentialsException(" Invalid Username or Password  !!");
		}
		
	}
	
//	private void authenticate(String username, String password) throws Exception {
//		
//		try {
//			
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//			
//		} catch (DisabledException e) {
//			throw new Exception("USER DISABLED");
//		}catch (BadCredentialsException e) {
//			throw new Exception("Invalid credentials");
//		}
//		
//	}
	@ExceptionHandler(BadCredentialsException.class)
	public String exceptionHandler(Exception e) {
		System.out.println(e);
		return "Credentials Invalid bruh!!";
	}
	
	//return details of current user
	@GetMapping("/current-user")
	public User getCurrentUser(Principal principal) {
		return (User) this.userDetailsService.loadUserByUsername(principal.getName());
	}
}
