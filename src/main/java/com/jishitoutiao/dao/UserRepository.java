package com.jishitoutiao.dao;

import com.jishitoutiao.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    // 通过用户名查找用户
    User findByUsername(String username);

    // 通过用户名/手机号查找用户
    User findByUsernameOrPhone(String username, String phone);

    // 仅手机号登录
    User findByPhone(String phone);

    // 用户登录(通过用户名密码)
    User findByUsernameOrPhoneAndPassword(String username, String phone, String password);
}
