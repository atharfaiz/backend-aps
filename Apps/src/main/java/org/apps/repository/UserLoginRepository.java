package org.apps.repository;

import org.apps.model.UserLogin;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepository extends PagingAndSortingRepository<UserLogin, String>{

}
