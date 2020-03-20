package me.jiniworld.demo.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.jiniworld.demo.models.entities.SecurityUser;
import me.jiniworld.demo.models.entities.User;
import me.jiniworld.demo.repositories.UserRepository;

@RequiredArgsConstructor
@Service
public class SecurityUserService implements UserDetailsService {
	
	private Logger logger = LoggerFactory.getLogger(SecurityUserService.class);
	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> oUser = userRepository.findWithUserRolesByEmailAndDel(email, false);
		if(!oUser.isPresent()) {
			logger.info("존재하지 않는 아이디입니다: " + email);
			throw new UsernameNotFoundException(email);
		}
		return new SecurityUser(oUser.get());
	}
}