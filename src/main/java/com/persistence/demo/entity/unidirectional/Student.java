package com.persistence.demo.entity.unidirectional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String gender;

  /*
   * @JoinTable created SQL -
   * 
   * create table
   * student_class
   * (class_id bigint, student_id bigint not null, primary key (student_id))
   * 
   */
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinTable(name = "student_class", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "class_id"))
  private StdClass stdClass;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public StdClass getStdClass() {
    return stdClass;
  }

  public void setStdClass(StdClass stdClass) {
    this.stdClass = stdClass;
  }

  @Override
  public String toString() {
    return "Student [id=" + id + ", name=" + name + ", gender=" + gender + ", stdClass=" + stdClass + "]";
  }

}
