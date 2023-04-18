package com.persistence.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.persistence.demo.entity.Post;
import com.persistence.demo.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

  @Autowired
  PostRepository postRepository;

  @Override
  @Transactional
  public List<Post> getAllPosts() {
    System.out.println("In PostServiceImpl");
    return postRepository.findAll();
  }

}
