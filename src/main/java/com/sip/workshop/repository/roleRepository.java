package com.sip.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sip.workshop.entities.Role;

@Repository("roleRepository")
public interface roleRepository extends JpaRepository<Role, Integer> {
	Role findByRole(String role);
}
