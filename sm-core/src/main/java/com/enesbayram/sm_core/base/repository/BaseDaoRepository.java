package com.enesbayram.sm_core.base.repository;

import com.enesbayram.sm_core.base.model.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseDaoRepository <T extends BaseEntity> extends JpaRepository<T , String> {
}
