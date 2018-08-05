package com.jishitoutiao.dao;

import com.jishitoutiao.domain.InformationIllegalityPond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InformationIllegalityPondRepository extends JpaRepository<InformationIllegalityPond, String>, JpaSpecificationExecutor<InformationIllegalityPond> {
}
