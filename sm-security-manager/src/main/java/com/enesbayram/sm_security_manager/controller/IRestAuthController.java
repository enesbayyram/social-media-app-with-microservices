package com.enesbayram.sm_security_manager.controller;

import com.enesbayram.sm_core.base.dto.DtoUserDef;
import com.enesbayram.sm_core.base.dto.DtoUserDefIU;
import com.enesbayram.sm_core.base.model.base.RootEntity;
import com.enesbayram.sm_security_manager.api.model.AuthRequest;
import com.enesbayram.sm_security_manager.api.model.RefreshTokenRequest;
import com.enesbayram.sm_security_manager.service.model.JWT;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IRestAuthController {


    public RootEntity<DtoUserDef> register(DtoUserDefIU dtoUserDefIU , MultipartFile profilePhoto) throws IOException;

    public RootEntity<JWT> authenticate(AuthRequest authRequest);

    public RootEntity<JWT> refreshToken(RefreshTokenRequest request);

}
