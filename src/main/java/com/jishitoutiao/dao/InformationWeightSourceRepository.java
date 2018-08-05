package com.jishitoutiao.dao;

import com.jishitoutiao.domain.InformationWeightSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InformationWeightSourceRepository extends JpaRepository<InformationWeightSource, String>, JpaSpecificationExecutor<InformationWeightSource> {
}
