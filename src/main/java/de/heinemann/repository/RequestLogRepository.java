package de.heinemann.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.heinemann.domain.RequestLog;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {
	
}
