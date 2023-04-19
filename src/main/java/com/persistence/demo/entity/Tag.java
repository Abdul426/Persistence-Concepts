package com.persistence.demo.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tag")
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String code;
  private String description;

  /*
   * On the target side, we only have to provide the name of the field, which maps
   * the relationship.
   * 
   * Keep in mind that since a many-to-many relationship doesn't have an owner
   * side in the database, we could configure the join table in the Tag class and
   * reference it from the Post class.
   */
  @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL)
  private Set<Post> posts = new HashSet<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<Post> getPosts() {
    return posts;
  }

  public void setPosts(Set<Post> posts) {
    this.posts = posts;
  }

  @Override
  public String toString() {
    List<Long> postIDs = null;
    StringBuilder sb = new StringBuilder("Tag [id=" + id + ", code=" + code + ", description=" + description);

    if (posts != null) {
      postIDs = this.posts.stream().map(post -> post.getId()).collect(Collectors.toList());
      sb.append(", postIDs=" + postIDs + "]");
    } else {
      sb.append("]");
    }

    return sb.toString();
  }

}
