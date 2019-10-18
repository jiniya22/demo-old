package me.jiniworld.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.jiniworld.demo.models.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findById(Long id);
	
	List<User> findAllByDel(boolean del);

	List<User> findAllByDelOrderByIdDesc(boolean del, Pageable pageable);
}
