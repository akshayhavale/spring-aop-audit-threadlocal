package com.app.services.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.configuration.ThreadLocalUtils;
import com.app.entity.user.User;
import com.app.repository.user.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User createUser(User user) {

		user = userRepository.save(user);
		// Approach 2
		ThreadLocalUtils.setThreadLocalValue(user);
		return user;
	}

	@Override
	public User getUserById(Long id) {
		// Approach 1
//		ThreadLocal<String> threadLocal = ThreaLocalDto.getThreadLocal();
//		threadLocal.set("Get user by id is called");

		// Approach 2
		ThreadLocalUtils.setThreadLocalValue("Get user by id is called");
		Optional<User> user = userRepository.findById(id);
		return user.isPresent() ? user.get() : null;
	}

	@Override
	public User getUserByUserName(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		return user.isPresent() ? user.get() : null;
	}

	@Override
	public List<User> getAllUsers() {
		// Approach 1
//		ThreadLocal<String> threadLocal = ThreaLocalDto.getThreadLocal();
//		threadLocal.set("Collected all the users");

		// Approach 2
		ThreadLocalUtils.setThreadLocalValue("Collected all the users");
		return userRepository.findAll();
	}

	@Override
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public void deleteUserByUsername(String username) {
		userRepository.deleteByUsername(username);

	}

	@Override
	public User updateUser(User user, Long id) {
		Optional<User> optUser = userRepository.findById(id);
		if (optUser.isPresent()) {
			User updatableUser = optUser.get();
			updatableUser.setMetadata(user.getMetadata());
			return userRepository.save(updatableUser);
		} else {
			return null;
		}
	}

}
