package com.app.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.app.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "from User u where u.username =:username")
	Optional<User> findByUsername(@Param(value = "username") String username);

	@Modifying
	@Transactional
	@Query(value = "delete from User u where u.username =:username")
	void deleteByUsername(@Param(value = "username") String username);

}
