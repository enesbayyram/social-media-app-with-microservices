package com.enesbayram.sm_security_manager.service;

import com.enesbayram.sm_core.base.dto.DtoUserDef;
import com.enesbayram.sm_core.base.dto.DtoUserDefIU;
import com.enesbayram.sm_security_manager.api.model.AuthRequest;
import com.enesbayram.sm_security_manager.api.model.RefreshTokenRequest;
import com.enesbayram.sm_security_manager.service.model.JWT;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IAuthService  {

    public DtoUserDef register(DtoUserDefIU dtoUserDefIU , MultipartFile profilePhoto) throws IOException;

    public JWT authenticate(AuthRequest authRequest);

    public JWT refreshToken(RefreshTokenRequest request);
}
