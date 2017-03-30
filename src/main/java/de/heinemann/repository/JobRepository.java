package de.heinemann.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.heinemann.domain.Job;
import de.heinemann.domain.User;

public interface JobRepository extends JpaRepository<Job, Long> {

	public List<Job> findByUserOrderByCreatedDesc(User user);
	
}
