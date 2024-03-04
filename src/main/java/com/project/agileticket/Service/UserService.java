package com.project.agileticket.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.agileticket.DAO.IUserRepository;
import com.project.agileticket.Pojo.User;

@Service
public class UserService implements IUserService{
	
	@Autowired
	private IUserRepository userRepository;

	@Override
	public User registerUser(User user) {
		// TODO Auto-generated method stub
		try
		{
			userRepository.save(user);
			System.out.print("registration succefully saved");
			return user;
		}
		catch(Exception e)
		{
			return null;
		}
	}


	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.getUsers();
	}

	@Override
	public User findUserById(int userId) {
		// TODO Auto-generated method stub
		return userRepository.findUserById(userId);
	}

	@Override
	public User getUserbyUsername(String name) {
		// TODO Auto-generated method stub
		
		return userRepository.getUserByUsername(name);
	}
	public User validateUserByCredentials(String name, String password, String employmentType)
	{
		
		List<User>users = userRepository.getUsers();
		
		
		for(User user:users)
		{
			System.out.print(user.getId()+" "+user.getName()+" "+user.getPassword()+" "+user.getEmploymentType()+"\n");
			if(user.getName().toLowerCase().equals(name))
			{
				System.out.println("\nValid name "+name+"="+user.getName());
				if(user.getPassword().equals(password))
				{
					System.out.println("\nValid password "+password+"="+user.getPassword());
					if(user.getEmploymentType().toLowerCase().equals(employmentType))
					{
						System.out.println("\nValid employmentType "+employmentType+"="+user.getEmploymentType());
						return user;
					}
				}
			}
			System.out.print("condition failed\n");
		}
		return null;
	}
	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userRepository.getUsers();
	}

	@Override
	public void updateUserByUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mergeUserByUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User deleteUserByUser(User user) {
		// TODO Auto-generated method stub
		try {
			System.out.print("Reached deleteUserByuser with id= "+user.getId()+"\n");
			userRepository.delete(user);
			return user;
		}
		catch(Exception e)
		{
			System.out.print(e.getMessage());
			System.out.print(e.getStackTrace());
			return null;
		}
	}

	@Override
	public void deleteUserByUserId(int userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User updateUserByUserId(int userId, User updatingUser) {
		// TODO Auto-generated method stub
		try {
			userRepository.merge(updatingUser);
			return updatingUser;
		}
		catch(Exception e)
		{
			System.out.print(e.getMessage());
			System.out.println(e.getStackTrace());
			return null;
		}
	}

}
