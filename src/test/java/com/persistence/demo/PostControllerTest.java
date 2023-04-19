package com.persistence.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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
import com.persistence.demo.entity.Tag;
import com.persistence.demo.repository.PostRepository;
import com.persistence.demo.repository.TagRepository;
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

	@Autowired
	private TagRepository tagRepository;

	private List<Post> postsFromDB = null;

	// Data not loading from initialize.sql
	@BeforeEach
	@Sql(scripts = { "initialize.sql" })
	public void beforeEach() {
		System.out.println("##### Inserting Data");
		System.out.println("##### Before Inserting, posts size: " + postRepository.findAll().size());

		Post p1 = new Post();
		// We cannot set Id of an entity while using persistance framework, PF will pick
		// from mentioned type
		p1.setId(22L);
		p1.setPost("Lazy loading issue in spring boot unit testing");

		PostComment post1Comment = new PostComment();

		/*
		 * We cannot set id for an entity while saving. Persistence will pick based on
		 * generation type
		 * If we do not set all IDs in entities, we will not be able to fetch from
		 * parent and target entity. This applies to 1-> * and * -> * mappings.
		 * 
		 */
		post1Comment.setId(222L);
		post1Comment.setComment("Please add @Transactional on test method");

		PostComment post1Comment2 = new PostComment();
		post1Comment2.setComment("Ok Thanks");

		Tag t1 = new Tag();
		t1.setCode("SPRING_BOOT");
		t1.setDescription("All spring boot related quetions");

		Tag t2 = new Tag();
		t2.setCode("SPRING_BOOT_JPA");
		t2.setDescription("All spring boot jpa related quetions");

		Set<Post> posts = new HashSet<>();
		Set<PostComment> post1Comments = new HashSet<>();
		post1Comments.add(post1Comment);
		post1Comments.add(post1Comment2);

		Set<Tag> p1Tags = new HashSet<>();
		// Set<Tag> p2Tags = new HashSet<>();

		posts.add(p1);
		p1.addPostComment(post1Comment);
		p1.addPostComment(post1Comment2);

		p1.addTag(t1);

		postRepository.save(p1);
		System.out.println("##### After Inserting, posts size: " + postRepository.findAll().size());
		postsFromDB = postRepository.findAll();
		System.out.println("##### Data: " + postsFromDB.get(0));
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
	void testFindAllPosts() throws Exception {

		// Here postRepository.findAll() is not fetching child...
		// Its loading post with post id : 1, we have not inserted this.
		// Its not UT, bcz we are reaching DB.
		when(postService.getAllPosts())
				.thenReturn(postsFromDB);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts"))
				.andExpect(status().isOk()).andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();
		String contentAsString = response.getContentAsString();
		System.out.println(contentAsString);

		assertEquals(contentAsString.contains("Transactional"), true);

	}

	@Test
	@Transactional
	public void testGetAllTags() {

		System.out.println("\n\n\n STARTING testGetAllTags \n\n\n\n");

		// MvcResult mvcResult =
		// mockMvc.perform(MockMvcRequestBuilders.get(tagRepository.findAll()));
		List<Tag> tags = tagRepository.findAll();
		System.out.println(" TAGS :: " + tags);
		System.out.println("\n\n\n End testGetAllTags \n\n\n\n");

	}
}
