package org.apps.repository;

import javax.transaction.Transactional;

import org.apps.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Integer>{
	
	User findByName(String userName);

}
