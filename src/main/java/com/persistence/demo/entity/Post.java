package com.persistence.demo.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "post")
public class Post {

  /*
   * IDENTITY picked auto_increment in MySQL
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String post;

  /*
   * Where mappedBy is available here?
   * By including the mappedBy attribute in the Post class, we mark it as the
   * inverse side.
   * 
   * @JsonManagedReference to resolve - Infinite loop while fetching.
   */
  @JsonManagedReference
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  Set<PostComment> postComments = new HashSet<>();;

  /*
   * Additionally, we have to configure how to model the relationship in the
   * RDBMS.The owner side is where we configure the relationship. We'll use the
   * Post class. We can do this with the @JoinTable annotation in the Post class.
   * 
   * Note that using @JoinTable or even @JoinColumn isn't required. JPA will
   * generate the table and column names for us. However, the strategy JPA uses
   * won't always match the naming conventions we use. So, we need the possibility
   * to configure table and column names.
   * 
   * If we do not have cascade type, jpa will not perform any operation for the
   * dependent entity.
   * 
   */
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "post_tag", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<Tag> tags = new HashSet<>();;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPost() {
    return post;
  }

  public void setPost(String post) {
    this.post = post;
  }

  public Set<PostComment> getPostComments() {
    return postComments;
  }

  public void setPostComments(Set<PostComment> postComments) {
    this.postComments = postComments;
  }

  public Set<Tag> getTags() {
    return tags;
  }

  public void setTags(Set<Tag> tags) {
    this.tags = tags;
  }

  @Override
  public String toString() {
    return "Post [id=" + id + ", post=" + post + ", postComments=" + postComments + ", tags=" + tags + "]";
  }

}
