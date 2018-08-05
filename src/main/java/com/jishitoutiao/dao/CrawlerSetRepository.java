package com.jishitoutiao.dao;

import com.jishitoutiao.domain.CrawlerSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CrawlerSetRepository extends JpaRepository<CrawlerSet, String>, JpaSpecificationExecutor<CrawlerSet> {
}
