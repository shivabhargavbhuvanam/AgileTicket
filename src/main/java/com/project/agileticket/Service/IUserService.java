package com.project.agileticket.Service;

import java.util.List;

import com.project.agileticket.Pojo.User;

public interface IUserService {
	User registerUser(User user);
    List<User> getAllUsers();
    public User findUserById(int userId);
	public User getUserbyUsername(String name);
	public List<User> getUsers();
	public User validateUserByCredentials(String name, String password, String employmentType);
	public void updateUserByUser(User user);
	public void mergeUserByUser(User user);
	public User deleteUserByUser(User user);
	
	public void deleteUserByUserId(int userId);
	public User updateUserByUserId(int userId, User updatingUser);
}
