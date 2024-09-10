package com.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.Users;
import com.demo.repositories.UserRepository;

@Service
public class UsersServiceImplementation implements UsersService {

	@Autowired
	UserRepository repo;

	@Override
	public String addUser(Users user) {

		repo.save(user);
		return "user is created and saved";
	}

	@Override
	public boolean emailExists(String email) {
		// TODO Auto-generated method stub
		if(repo.findByEmail(email)==null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	@Override
	public boolean validateUser(String email, String password) 
	{

		Users user = repo.findByEmail(email);
		String db_password = user.getPassword();
		if(db_password.equals(password))
		{
			return true;
		}
		else
		{
			return false;
		}

	}

	@Override
	public String getRole(String email) {
		
		Users user=repo.findByEmail(email);
		return user.getRole();
	}

	@Override
	public Users getUser(String email) {
		
		return repo.findByEmail(email);
	}

	@Override
	public void updateUser(Users user) {
		
		repo.save(user);
		
	}

	@Override
	public String getUserName(String email) {
		
		Users user = repo.findByEmail(email);
		String username = user.getUsername();
		return username;
	}

}
