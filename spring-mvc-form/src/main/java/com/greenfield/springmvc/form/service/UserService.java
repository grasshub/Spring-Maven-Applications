package com.greenfield.springmvc.form.service;

import java.util.List;

import com.greenfield.springmvc.form.model.User;

public interface UserService {
	
	public User finaById(Integer id);
	
	public List<User> findAll();
	
	public void saveOrUpdate(User user);
	
	public void delete(Integer id);

}
