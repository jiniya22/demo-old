package me.jiniworld.demo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jiniworld.demo.models.entities.SecurityUser;
import me.jiniworld.demo.models.entities.User;
import me.jiniworld.demo.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SecurityUserService implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> oUser = userRepository.findWithUserRolesByEmailAndDel(email, false);
		if(!oUser.isPresent()) {
			log.info("존재하지 않는 아이디입니다: " + email);
			throw new UsernameNotFoundException(email);
		}
		return new SecurityUser(oUser.get());
	}
}