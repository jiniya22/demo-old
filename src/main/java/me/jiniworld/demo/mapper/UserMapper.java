package me.jiniworld.demo.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Select;

import me.jiniworld.demo.models.entities.User;

public interface UserMapper {
	
	public Optional<User> selectUser(long id);
	
	@Select("SELECT * FROM user where id = #{id}")
	public Optional<User> selectUser2(long id);
}
