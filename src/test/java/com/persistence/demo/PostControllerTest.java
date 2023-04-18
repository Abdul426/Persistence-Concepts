package com.persistence.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.persistence.demo.entity.Post;
import com.persistence.demo.entity.PostComment;
import com.persistence.demo.repository.PostRepository;
import com.persistence.demo.service.PostService;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-dev.properties")
@AutoConfigureMockMvc
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	PostRepository postRepository;

	@MockBean
	private PostService postService;

	private List<Post> posts = null;

	@Test
	void contextLoads() {
	}

	// Data not loading from initialize.sql
	@BeforeEach
	@Sql(scripts = { "initialize.sql" })
	public void beforeEach() {
		System.out.println("##### Inserting Data");
		System.out.println("##### " + postRepository.findAll().size());
		Post p = new Post();
		// We cannot set Id of an entity while using persistance framework, PF will pick
		// from mentioned type
		p.setId(22L);
		p.setPost("Lazy loading issue in spring boot unit testing");

		PostComment postComment = new PostComment();
		// Persistence will not pick this
		postComment.setId(222L);
		postComment.setComment("Please add @Transactional on test method");
		// postComment.setPost(p);

		Set<PostComment> pSet = new HashSet<>();
		pSet.add(postComment);
		p.setPostComments(pSet);
		postRepository.save(p);
		System.out.println("After Saving ##### " + postRepository.findAll().size());
		posts = postRepository.findAll();
		System.out.println("##### Entity0: " + posts.get(0));
	}

	@AfterEach
	public void afterEach() {
		System.out.println("##### After Each ... Deleting DB Data");
		postRepository.deleteAll();
		System.out.println("##### " + postRepository.findAll().size());
	}

	/*
	 * If you don't have @Transactional child entities would not load.
	 * Exception will be thrown - org.hibernate.LazyInitializationException: failed
	 * to lazily initialize a collection of role:
	 * com.persistence.demo.entity.Post.postComments, could not initialize proxy -
	 * no Session
	 */
	@Test
	@Transactional
	void testFindAll() throws Exception {

		//Here postRepository.findAll() is not fetching child...
		//Its loading post with post id : 1, we have not inserted this.
		//Its not UT, bcz we are reaching DB.
		when(postService.getAllPosts())
                .thenReturn(posts);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts"))
				.andExpect(status().isOk()).andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();
		String contentAsString = response.getContentAsString();
		System.out.println(contentAsString);

		assertEquals(contentAsString.contains("Transactional"), true);

	}
}
