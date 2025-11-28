package com.enesbayram.sm_security_manager.repository;

import com.enesbayram.sm_core.base.model.RefreshToken;
import com.enesbayram.sm_core.base.repository.BaseDaoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RefreshTokenRepository extends BaseDaoRepository<RefreshToken> {

    @Query(value = "from RefreshToken r WHERE r.refreshToken = :refreshToken")
    public Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
