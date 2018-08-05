package com.jishitoutiao.dao;

import com.jishitoutiao.domain.InformationWeightSort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InformationWeightSortRepository extends JpaRepository<InformationWeightSort, String>, JpaSpecificationExecutor<InformationWeightSort> {
}
