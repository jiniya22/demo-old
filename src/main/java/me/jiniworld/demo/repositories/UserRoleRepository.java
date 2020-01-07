package me.jiniworld.demo.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.jiniworld.demo.models.entities.User;
import me.jiniworld.demo.models.entities.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
	
	Set<UserRole> findByUserAndDel(User user, boolean del);
	
}