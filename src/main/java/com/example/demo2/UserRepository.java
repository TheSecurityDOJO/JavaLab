package com.example.demo2;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Deal, Long> {
	
}

