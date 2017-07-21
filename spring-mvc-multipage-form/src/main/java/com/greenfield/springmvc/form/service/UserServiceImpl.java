package com.greenfield.springmvc.form.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenfield.springmvc.form.dao.UserDAO;
import com.greenfield.springmvc.form.model.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	UserDAO userDAO;
	
	public UserServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public User finaById(Integer id) {
		return userDAO.finaById(id);
	}

	public List<User> findAll() {
		return userDAO.findAll();
	}

	public void saveOrUpdate(User user) {
		userDAO.saveOrUpdate(user);	
	}

	public void delete(Integer id) {
		userDAO.delete(id);		
	}

}
