package org.apps.repository;

import org.apps.model.Test;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TestRepository extends PagingAndSortingRepository<Test, Integer> {

}
