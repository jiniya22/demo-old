package me.jiniworld.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import me.jiniworld.demo.models.entities.User;

@Repository
@RepositoryRestResource(collectionResourceRel = "entry", path = "users")
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findById(Long id);
	
}
