package me.jiniworld.demo.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import me.jiniworld.demo.models.entities.SecurityUser;
import me.jiniworld.demo.models.entities.User;
import me.jiniworld.demo.repositories.UserRepository;
import me.jiniworld.demo.repositories.UserRoleRepository;

@Service
public class SecurityUserService implements UserDetailsService {
	
	private Logger logger = LoggerFactory.getLogger(SecurityUserService.class);
	
	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	
	@Autowired
	public SecurityUserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> oUser = userRepository.findByEmailAndDel(email, false);
		if(!oUser.isPresent()) {
			logger.info("존재하지 않는 아이디입니다: " + email);
			throw new UsernameNotFoundException(email);
		}
		
		User user = oUser.get();
		return new SecurityUser(user, userRoleRepository.findByUserAndDel(user, false));
	}
}