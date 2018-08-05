package com.jishitoutiao.dao;

import com.jishitoutiao.domain.InformationIllegalityResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InformationIllegalityResultRepository extends JpaRepository<InformationIllegalityResult, String>, JpaSpecificationExecutor<InformationIllegalityResult> {
}
