package com.jishitoutiao.dao;

import com.jishitoutiao.domain.InformationComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InformationCommentRepository extends JpaRepository<InformationComment, String>, JpaSpecificationExecutor<InformationComment> {
}
