package me.jiniworld.demo.services;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.jiniworld.demo.models.entities.Store;
import me.jiniworld.demo.models.entities.User;
import me.jiniworld.demo.models.values.StoreValue;
import me.jiniworld.demo.models.values.StoreValue;
import me.jiniworld.demo.repositories.StoreRepository;

@Service
public class StoreService {

	private final StoreRepository storeRepository;
	
	@Autowired
	public StoreService(StoreRepository storeRepository) {
		this.storeRepository = storeRepository;
	}
	
	public Optional<Store> findById(Long id) {
		return storeRepository.findById(id);
	}
	
	@Transactional
	public Store save(StoreValue value) {
		
		Store store = Store.builder()
				.name(value.getName())
				.storeBusiness(value.getStoreBusiness()).build();		// FIXME userId -> user 
		
		return storeRepository.save(store);
	}
	
	@Transactional
	public void patch(Store store, StoreValue value) {
		if(StringUtils.isNotBlank(value.getName()))
			store.setName(value.getName());
		if(StringUtils.isNotBlank(value.getStoreBusiness()))
			store.setStoreBusiness(value.getStoreBusiness());
		// user
	}

	@Transactional
	public void delete(Store store) {
//		store.setDel(true);
		storeRepository.delete(store);
	}

	public List<Store> findAll(Pageable pageable) {
		return storeRepository.findAllByDelOrderByIdDesc(false, pageable);
	}
}
