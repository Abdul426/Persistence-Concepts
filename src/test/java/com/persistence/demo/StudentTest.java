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

import com.persistence.demo.entity.unidirectional.StdClass;
import com.persistence.demo.entity.unidirectional.Student;
import com.persistence.demo.repository.StudentRepository;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-dev.properties")
@AutoConfigureMockMvc
public class StudentTest {

  @Autowired
  private StudentRepository studentRepository;

  @BeforeEach
  public void beforeEach() {

    StdClass stdClass = new StdClass();
    stdClass.setCode("CLS_1");
    stdClass.setName("1st Standard");

    Student student = new Student();
    student.setName("Abdul");
    student.setStdClass(stdClass);

    studentRepository.save(student);

    studentRepository.findAll();
  }

  @AfterEach
  public void afterEach() {
    System.out.println("##### After Each ... Deleting DB Data");
    studentRepository.deleteAll();
    System.out.println("##### " + studentRepository.findAll().size());
  }

  @Test
  @Transactional
  public void testGetAllStds() {

    System.out.println("\n\n\n STARTING testGetAllStds \n\n\n\n");

    List<Student> students = studentRepository.findAll();
    System.out.println(" Studetns :: " + students);
    System.out.println("\n\n\n End testGetAllStds \n\n\n\n");

  }
}
