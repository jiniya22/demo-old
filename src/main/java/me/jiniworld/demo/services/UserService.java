package me.jiniworld.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.jiniworld.demo.models.entities.User;
import me.jiniworld.demo.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}
}
