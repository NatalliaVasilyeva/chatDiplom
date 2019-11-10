package com.bsu.nvasilyeva.dao;

import com.bsu.nvasilyeva.entity.Message;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class MessageDAO {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public void add(Message message) {
		entityManager.
				persist(message);
	}

	@Transactional
	public Message findById(int id) {

		return entityManager.
				find(Message.class, id);
	}

}
