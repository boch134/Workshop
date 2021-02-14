package com.sip.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sip.workshop.entities.User;

@Repository("userRepository")
public interface userRepository extends JpaRepository<User, Integer> {
	User findByEmail(String email);
}