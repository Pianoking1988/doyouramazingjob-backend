package de.heinemann.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.heinemann.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByMailIgnoreCase(String mail);
	
}
