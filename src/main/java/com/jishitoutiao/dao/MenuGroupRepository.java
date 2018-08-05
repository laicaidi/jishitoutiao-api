package com.jishitoutiao.dao;

import com.jishitoutiao.domain.MenuGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MenuGroupRepository extends JpaRepository<MenuGroup, String>, JpaSpecificationExecutor<MenuGroup> {
}
