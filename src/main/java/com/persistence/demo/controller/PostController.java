package com.persistence.demo.controller;

import java.util.List;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.persistence.demo.entity.Post;
import com.persistence.demo.service.PostService;

@RestController
@RequestMapping("api/v1")
public class PostController implements InitializingBean, DisposableBean {

  @Autowired
  PostService postService;

  // name = with this it was not working
  @GetMapping("/posts")
  public ResponseEntity<List<Post>> getPosts() {
    System.out.println("In PostController getPosts");
    List<Post> posts = postService.getAllPosts();
    return new ResponseEntity<>(posts, HttpStatus.OK);
  }

  @Override
  public void destroy() throws Exception {
    System.out.println("In PostController Bean Life Cycle destroy");
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("In PostController Bean Life Cycle afterPropertiesSet()");
  }
}
