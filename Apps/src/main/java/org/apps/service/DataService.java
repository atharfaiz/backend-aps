package org.apps.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

@Component
public class DataService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Object findObject() {
		Query q= entityManager.createNativeQuery("SELECT name FROM user");
		return q.getResultList();
	}

}
