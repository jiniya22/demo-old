package me.jiniworld.demo.mapper;

import java.util.Optional;

import me.jiniworld.demo.models.entities.Store;

public interface StoreMapper {
	
	public Optional<Store> selectStore(long id);
}
