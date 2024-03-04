package com.project.agileticket.DAO;

import java.util.List;

import com.project.agileticket.Pojo.User;

public interface IUserRepository{
	
	public User findUserById(int userId);
	public List<User> getUsers();
	public void save(User user);
	public void update(int userid, User user);
	public void merge(User user);
	public void delete(User user);
	public User getUserByUsername(String name);
}
