package com.jishitoutiao.dao;

import com.jishitoutiao.domain.InformationOutputList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InformationOutputListRepository extends JpaRepository<InformationOutputList, String>, JpaSpecificationExecutor<InformationOutputList> {
}
