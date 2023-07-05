package com.exam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JwtResponse {
	
	private String jwtToken;
	private String username;

//	public JwtResponse() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	public JwtResponse(String token) {
//		super();
//		this.token = token;
//	}
//
//	public String getToken() {
//		return token;
//	}
//
//	public void setToken(String token) {
//		this.token = token;
//	}
	

}
