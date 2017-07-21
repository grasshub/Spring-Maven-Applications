package com.greenfield.springmvc.form.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.greenfield.springmvc.form.model.User;

@Repository
public class UserDAOImpl implements UserDAO {
	
	private HibernateTemplate  hibernateTemplate;
	
	public UserDAOImpl(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	// In order to initialize list of lazily loaded framework and skill fields before transaction is committed and session is closed,
	// we need to utilize Hibernate class initialize method to fetch these two lists before transaction is committed.
	// This strategy will resolve standalone application issue with accessing lazily loaded list field after session is closed and could
	// not retrieve the list through the closed session.
	public User finaById(Integer id) {
		
		User user = hibernateTemplate.get(User.class, id);
		
		// Initialize lazily loaded framework and skill list before the transaction is committed and session is closed automatically.
		Hibernate.initialize(user.getFramework());
		Hibernate.initialize(user.getSkill());
		
		return user;
	}

	public List<User> findAll() {
		
		List<User> userList = hibernateTemplate.loadAll(User.class);
		// Initialize lazily loaded framework and skill list before the transaction is committed and session is closed automatically.
		for (User user : userList) {
			Hibernate.initialize(user.getFramework());
			Hibernate.initialize(user.getSkill());
		}
		
		return userList;
	}

	public void saveOrUpdate(User user) {
		hibernateTemplate.saveOrUpdate(user);	
	}

	public void delete(Integer id) {
		User user = finaById(id);
		hibernateTemplate.delete(user);		
	}

}
