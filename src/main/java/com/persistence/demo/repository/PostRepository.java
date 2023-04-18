package com.persistence.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.persistence.demo.entity.Post;

/*
 * It should be Entity, Id-type
 */
@Repository // This is optional. Why?
public interface PostRepository extends JpaRepository<Post, Long> {

}
