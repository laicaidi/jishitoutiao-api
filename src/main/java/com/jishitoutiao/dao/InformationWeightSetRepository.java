package com.jishitoutiao.dao;

import com.jishitoutiao.domain.InformationWeightSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InformationWeightSetRepository extends JpaRepository<InformationWeightSet, String>, JpaSpecificationExecutor<InformationWeightSet> {
}
