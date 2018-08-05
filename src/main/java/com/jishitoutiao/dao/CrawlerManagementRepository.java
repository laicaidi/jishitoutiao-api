package com.jishitoutiao.dao;

import com.jishitoutiao.domain.CrawlerManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CrawlerManagementRepository extends JpaRepository<CrawlerManagement, String>, JpaSpecificationExecutor<CrawlerManagement> {
}
