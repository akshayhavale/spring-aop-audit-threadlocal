package com.app.repository.userlogin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.controller.user.UserLogin;

public interface UserLoginRespository extends JpaRepository<UserLogin, Long>{

}
