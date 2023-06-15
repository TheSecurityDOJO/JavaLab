package com.laba4s;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pojo.DealPojo;


@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {
	
	
	@Query(value = "SELECT new pojo.DealPojo(d.description, d.link, d.name, d.validated) FROM Deal d WHERE d.session = ?1 OR d.session IS NULL")
	List<DealPojo> customFind(String session);

}
