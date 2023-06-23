package com.app.controller.user;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.custom.annatations.EnableAuditing;
import com.app.entity.user.User;
import com.app.services.user.UserService;

@EnableAuditing
@RestController
@RequestMapping(value = "/api/user")
public class UserApi {

	private final UserService userService;

	private static final String USERNAME = "username";
	private static final String SUCCESS = "SUCCESS";

	public UserApi(UserService userService) {
		this.userService = userService;
	}

	@EnableAuditing
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	@EnableAuditing
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody User getUserById(@PathVariable(value = "id", required = true) Long id) {
		return userService.getUserById(id);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping(value = "/username", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody User getUserByUsername(@RequestBody Map<String, String> usernameMap) {
		return userService.getUserByUserName(usernameMap.get(USERNAME));
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/")
	public @ResponseBody List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@ResponseStatus(value = HttpStatus.ACCEPTED)
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody User updateUser(@PathVariable(value = "id", required = true) Long id, @RequestBody User user) {
		return userService.updateUser(user, id);
	}

	@ResponseStatus(value = HttpStatus.ACCEPTED)
	@DeleteMapping(value = "/{id}")
	public @ResponseBody String deleteUserById(@PathVariable(value = "id", required = true) Long id) {
		userService.deleteUserById(id);
		return SUCCESS;
	}

	@ResponseStatus(value = HttpStatus.ACCEPTED)
	@DeleteMapping(value = "/username", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String deleteUserByUsername(@RequestBody Map<String, String> usernameMap) {
		userService.deleteUserByUsername(usernameMap.get(USERNAME));
		return SUCCESS;
	}

}
