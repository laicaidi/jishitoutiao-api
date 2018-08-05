package com.jishitoutiao.dao;

import com.jishitoutiao.domain.CrawlerUserAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CrawlerUserAgentRepository extends JpaRepository<CrawlerUserAgent, String>, JpaSpecificationExecutor<CrawlerUserAgent> {
}
