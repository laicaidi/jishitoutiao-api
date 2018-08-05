package com.jishitoutiao.dao;

import com.jishitoutiao.domain.CrawlerDynamicIp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CrawlerDynamicIpRepository extends JpaRepository<CrawlerDynamicIp, String>, JpaSpecificationExecutor<CrawlerDynamicIp> {
}
