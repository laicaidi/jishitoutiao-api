package com.jishitoutiao.dao;

import com.jishitoutiao.domain.Role;
import com.jishitoutiao.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {
    // 查找角色
    Role findByRoleName(String roleName);
}
