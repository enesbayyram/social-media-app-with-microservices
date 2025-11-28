package com.enesbayram.sm_user_manager.repository;

import com.enesbayram.sm_core.base.model.UserDef;
import com.enesbayram.sm_core.base.repository.BaseDaoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDefRepository extends BaseDaoRepository<UserDef> {

    public Optional<UserDef> findByUsername(String username);

}
