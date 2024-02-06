package me.jiniworld.demo.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Select;

import me.jiniworld.demo.models.entities.User;

public interface UserMapper {
	
	Optional<User> findById(long id);
	
	@Select("SELECT * FROM user where id = #{id}")
	Optional<User> findById2(long id);
}
