package com.jishitoutiao.dao;

import com.jishitoutiao.domain.InformationRepetitionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InformationRepetitionResultRepository extends JpaRepository<InformationRepetitionResult, String>, JpaSpecificationExecutor<InformationRepetitionResult> {
}
