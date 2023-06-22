package com.app.services.user;

import java.util.List;

import com.app.entity.user.User;

public interface UserService {

	public User createUser(User user);

	public User getUserById(Long id);

	public User getUserByUserName(String username);

	public List<User> getAllUsers();

	public void deleteUserById(Long id);

	public void deleteUserByUsername(String username);

	public User updateUser(User user, Long id);

}
