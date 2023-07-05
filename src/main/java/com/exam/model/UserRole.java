package com.exam.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class UserRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userRoleID;
	
	//user
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	@ManyToOne
	private Role role;

	public Long getUserRoleID() {
		return userRoleID;
	}

	public void setUserRoleID(Long userRoleID) {
		this.userRoleID = userRoleID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public UserRole() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
