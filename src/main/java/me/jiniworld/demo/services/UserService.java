package me.jiniworld.demo.services;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.jiniworld.demo.models.entities.User;
import me.jiniworld.demo.models.entities.UserRole;
import me.jiniworld.demo.models.entities.UserRole.RoleType;
import me.jiniworld.demo.models.simples.UserSimple;
import me.jiniworld.demo.models.values.UserValue;
import me.jiniworld.demo.repositories.UserRepository;
import me.jiniworld.demo.repositories.UserRoleRepository;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserSimple findById(Long id) {
		return userRepository.findWithUserRolesById(id).map(User::getSimple).orElse(null);
	}

	private User save(UserValue value) {
		return userRepository.save(User.builder()
				.type(value.getType())
				.email(value.getEmail())
				.birthDate(value.getBirthDate())
				.name(value.getName())
				.password(passwordEncoder.encode(value.getPassword()))
				.phoneNumber(value.getPhoneNumber())
				.sex(value.getSex()).build());
	}
		
	private UserRole saveUserRole(User user, RoleType roleName) {
		return userRoleRepository.save(UserRole.builder().user(user).roleName(roleName).build());
	}

	@Transactional(transactionManager="transactionManager")
	public UserSimple join(UserValue value) {
		User user = save(value);
		saveUserRole(user, RoleType.ROLE_VIEW);
		return user.getSimple();
	}
	
	@Transactional
	public boolean patch(long id, UserValue value) {
		Optional<User> oUser = userRepository.findById(id);		
		if(!oUser.isPresent()) {
			return false;
		}
		User user = oUser.get();
		if(StringUtils.isNotBlank(value.getType()))
			user.setType(value.getType());
		if(StringUtils.isNotBlank(value.getEmail()))
			user.setEmail(value.getEmail());
		if(StringUtils.isNotBlank(value.getBirthDate()))
			user.setBirthDate(value.getBirthDate());
		if(StringUtils.isNotBlank(value.getName()))
			user.setName(value.getName());
		if(StringUtils.isNotBlank(value.getPassword()))
			user.setPassword(passwordEncoder.encode(value.getPassword()));
		if(StringUtils.isNotBlank(value.getPhoneNumber()))
			user.setPhoneNumber(value.getPhoneNumber());
		if(StringUtils.isNotBlank(value.getSex()))
			user.setSex(value.getSex());
		return true;
	}

	@Transactional
	public boolean delete(long id) {		
		Optional<User> oUser = userRepository.findById(id);
		if(!oUser.isPresent())
			return false;
		
		User user = oUser.get();
		user.setDel(true);
		return true;
	}

	public List<User> findAll(Pageable pageable) {
		return userRepository.findAllByDelOrderByIdDesc(false, pageable);
	}

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	
}
