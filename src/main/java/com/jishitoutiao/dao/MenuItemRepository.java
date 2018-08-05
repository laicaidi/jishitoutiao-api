package com.jishitoutiao.dao;

import com.jishitoutiao.domain.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MenuItemRepository extends JpaRepository<MenuItem, String>, JpaSpecificationExecutor<MenuItem> {
}
