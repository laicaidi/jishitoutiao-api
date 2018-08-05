package com.jishitoutiao.dao;

import com.jishitoutiao.domain.CrawlerSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CrawlerSourceRepository extends JpaRepository<CrawlerSource, String>, JpaSpecificationExecutor<CrawlerSource> {
}
