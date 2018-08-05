package com.jishitoutiao.dao;

import com.jishitoutiao.domain.InformationSource;
import com.jishitoutiao.domain.InformationWeightFiltrate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InformationWeightFiltrateRepository extends JpaRepository<InformationWeightFiltrate, String>, JpaSpecificationExecutor<InformationWeightFiltrate> {
}
