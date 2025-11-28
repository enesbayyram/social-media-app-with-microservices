package com.enesbayram.sm_security_manager.service;

import com.enesbayram.sm_core.base.model.RefreshToken;
import com.enesbayram.sm_core.base.model.UserDef;
import com.enesbayram.sm_core.base.service.IBaseCrudService;
import com.enesbayram.sm_core.base.service.IBaseDbService;

public interface IRefreshTokenService extends IBaseDbService<RefreshToken> , IBaseCrudService<RefreshToken> {

    public RefreshToken saveRefreshToken(UserDef userDef);

    public RefreshToken findByRefreshToken(String refreshToken);

    public boolean isRefreshTokenValid(RefreshToken refreshToken);
}
