package com.exam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.model.Role;
import com.exam.model.User;

public interface RoleRepository extends JpaRepository<User, Long>{

	void save(Role role);

}
