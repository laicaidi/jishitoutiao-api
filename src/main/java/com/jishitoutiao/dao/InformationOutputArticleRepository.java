package com.jishitoutiao.dao;

import com.jishitoutiao.domain.InformationIllegalitySet;
import com.jishitoutiao.domain.InformationOutputArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InformationOutputArticleRepository extends JpaRepository<InformationOutputArticle, String>, JpaSpecificationExecutor<InformationOutputArticle> {
}
