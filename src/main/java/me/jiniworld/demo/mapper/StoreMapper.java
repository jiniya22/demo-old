package me.jiniworld.demo.mapper;

import java.util.Optional;

import me.jiniworld.demo.models.entities.Store;

public interface StoreMapper {
	
	Optional<Store> findById(long id);
}

//@Component
//public class StoreMapper {
//	
//	@Autowired
//	private SqlSession sqlSessionTemplate;
//	
//	public Optional<Store> findById(long id) {
//		return Optional.ofNullable(sqlSessionTemplate.selectOne("me.jiniworld.demo.mapper.StoreMapper.findById", id));
//	}
//	
//}
