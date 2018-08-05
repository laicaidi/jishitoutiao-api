package com.jishitoutiao.dao;

import com.jishitoutiao.domain.InformationIllegalityResult;
import com.jishitoutiao.domain.InformationIllegalitySet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InformationIllegalitySetRepository extends JpaRepository<InformationIllegalitySet, String>, JpaSpecificationExecutor<InformationIllegalitySet> {
}
