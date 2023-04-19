package com.persistence.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.persistence.demo.entity.Tag;

/*
 * The @RepositoryRestResource annotation is optional and is used to customize
 * the REST endpoint. If we decided to omit it,
 * Spring would automatically create an endpoint at “/websiteUsers” instead of
 * “/users“.
 * Ex: public class WebsiteUser
 * 
 * collectionResourceRel - Its values will be named on JSON response object
 * path - Its values is the rest api path
 */
@RepositoryRestResource(collectionResourceRel = "tags", path = "tags")
public interface TagRepository extends JpaRepository<Tag, Long> {

}
