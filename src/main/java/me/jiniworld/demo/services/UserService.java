package me.jiniworld.demo.services;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.jiniworld.demo.mapper.UserMapper;
import me.jiniworld.demo.models.entities.User;
import me.jiniworld.demo.models.entities.UserRole;
import me.jiniworld.demo.models.entities.UserRole.RoleType;
import me.jiniworld.demo.models.values.UserValue;
import me.jiniworld.demo.repositories.UserRepository;
import me.jiniworld.demo.repositories.UserRoleRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}
	
	public Optional<User> findById(Long id) {
		return userRepository.findWithUserRolesById(id);
	}
	
	public Optional<User> findByIdUsingMapper(Long id) {
		return userMapper.findById(id);
	}
	
	public Optional<User> findByIdUsingMapper2(Long id) {
		return userMapper.findById2(id);
	}
	
	@Transactional
	public User save(UserValue value) {
		User user = User.builder()
				.type(value.getType())
				.email(value.getEmail())
				.birthDate(value.getBirthDate())
				.name(value.getName())
				.password(passwordEncoder.encode(value.getPassword()))
				.phoneNumber(value.getPhoneNumber())
				.sex(value.getSex()).build();
		return userRepository.save(user);
	}
	
	@Transactional
	public User join(UserValue value) {
		User user = userRepository.save(User.builder()
				.type(value.getType())
				.email(value.getEmail())
				.birthDate(value.getBirthDate())
				.name(value.getName())
				.password(passwordEncoder.encode(value.getPassword()))
				.phoneNumber(value.getPhoneNumber())
				.sex(value.getSex()).build());
		userRoleRepository.save(UserRole.builder().user(user).roleName(RoleType.ROLE_VIEW).build());
		return user;
	}
	
	@Transactional
	public void patch(User user, UserValue value) {
		if(StringUtils.isNotBlank(value.getType()))
			user.setType(value.getType());
		if(StringUtils.isNotBlank(value.getEmail()))
			user.setEmail(value.getEmail());
		if(StringUtils.isNotBlank(value.getBirthDate()))
			user.setBirthDate(value.getBirthDate());
		if(StringUtils.isNotBlank(value.getName()))
			user.setName(value.getName());
		if(StringUtils.isNotBlank(value.getPassword()))
			user.setPassword(value.getPassword());
		if(StringUtils.isNotBlank(value.getPhoneNumber()))
			user.setPhoneNumber(value.getPhoneNumber());
		if(StringUtils.isNotBlank(value.getSex()))
			user.setSex(value.getSex());
	}

	@Transactional
	public void delete(User user) {
//		user.setDel(true);
		userRepository.delete(user);
	}

	public List<User> findAll(Pageable pageable) {
		return userRepository.findAllByDelOrderByIdDesc(false, pageable);
	}
}
