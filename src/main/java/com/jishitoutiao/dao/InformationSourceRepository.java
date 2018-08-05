package com.jishitoutiao.dao;

import com.jishitoutiao.domain.InformationRepetitionResult;
import com.jishitoutiao.domain.InformationSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InformationSourceRepository extends JpaRepository<InformationSource, String>, JpaSpecificationExecutor<InformationSource> {
}
