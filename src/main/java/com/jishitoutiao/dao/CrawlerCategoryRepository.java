package com.jishitoutiao.dao;

import com.jishitoutiao.domain.CrawlerCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CrawlerCategoryRepository extends JpaRepository<CrawlerCategory, String>, JpaSpecificationExecutor<CrawlerCategory> {
}
