package me.jiniworld.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import me.jiniworld.demo.models.entities.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
	
	List<GrantedAuthority> findByUserIdAndDel(long userId, boolean del);
	
}