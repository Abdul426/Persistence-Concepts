package com.persistence.demo;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.persistence.demo.entity.unidirectional.Pan;
import com.persistence.demo.entity.unidirectional.Person;
import com.persistence.demo.repository.PersonRepository;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-dev.properties")
@AutoConfigureMockMvc
public class PersonTest {

	@Autowired
	PersonRepository personRepository;

	@BeforeEach
	public void beforeEach() {

		List<Person> persons = personRepository.findAll();
		System.out.println(persons);

		Pan pan = new Pan();
		pan.setNumber("12EDKJKDI");

		Person person = new Person();
		person.setName("ABUDL");
		person.setPan(pan);

		personRepository.save(person);

		persons = personRepository.findAll();
		System.out.println(persons);
	}

	@AfterEach
	public void afterEach() {
		System.out.println("##### After Each ... Deleting DB Data");
		personRepository.deleteAll();
		System.out.println("##### " + personRepository.findAll().size());
	}

	@Test
	@Transactional
	public void testOne2OneUDMapping() {

		System.out.println("\n\n\n STARTING testGetAllTags \n\n\n\n");

		List<Person> persons = personRepository.findAll();
		System.out.println(" PERSONS :: " + persons);
		System.out.println("\n\n\n End testGetAllTags \n\n\n\n");

	}
}
