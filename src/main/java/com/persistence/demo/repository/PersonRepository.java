package com.persistence.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.persistence.demo.entity.unidirectional.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
