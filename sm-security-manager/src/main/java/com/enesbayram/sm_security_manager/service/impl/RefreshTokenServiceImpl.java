package com.enesbayram.sm_security_manager.service.impl;

import com.enesbayram.sm_core.base.dto.DtoBase;
import com.enesbayram.sm_core.base.dto.DtoRefreshToken;
import com.enesbayram.sm_core.base.model.RefreshToken;
import com.enesbayram.sm_core.base.model.UserDef;
import com.enesbayram.sm_core.base.service.impl.BaseDbServiceImpl;
import com.enesbayram.sm_security_manager.repository.RefreshTokenRepository;
import com.enesbayram.sm_security_manager.service.IRefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenServiceImpl extends BaseDbServiceImpl<RefreshTokenRepository, RefreshToken> implements IRefreshTokenService {

    @Override
    public Class<? extends DtoBase> getDTOClassForService() {
        return DtoRefreshToken.class;
    }

    private RefreshToken createRefreshTokenModel(UserDef userDef) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCreateTime(LocalDateTime.now());
        refreshToken.setCreateUser("ebayram");
        refreshToken.setUserDef(userDef);
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpireIn(LocalDateTime.now().plusHours(4));
        return refreshToken;
    }

    @Override
    public RefreshToken saveRefreshToken(UserDef userDef) {
        return save(createRefreshTokenModel(userDef));
    }

    @Override
    public RefreshToken findByRefreshToken(String refreshToken) {
        return dao.findByRefreshToken(refreshToken).orElse(null);
    }

    @Override
    public boolean isRefreshTokenValid(RefreshToken refreshToken) {
        return refreshToken.getExpireIn().isAfter(LocalDateTime.now());
    }

}
