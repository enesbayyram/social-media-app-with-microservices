package com.enesbayram.sm_security_manager.controller;

import com.enesbayram.sm_core.base.dto.DtoUserDef;
import com.enesbayram.sm_core.base.dto.DtoUserDefIU;
import com.enesbayram.sm_core.base.model.base.RootEntity;
import com.enesbayram.sm_core.base.restservice.base.RestBaseController;
import com.enesbayram.sm_security_manager.api.model.AuthRequest;
import com.enesbayram.sm_security_manager.api.model.RefreshTokenRequest;
import com.enesbayram.sm_security_manager.service.IAuthService;
import com.enesbayram.sm_security_manager.service.model.JWT;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RestAuthControllerImpl extends RestBaseController implements  IRestAuthController {

    private final IAuthService authService;

    @PostMapping(path = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public RootEntity<DtoUserDef> register(@RequestPart("user") @Valid DtoUserDefIU dtoUserDefIU ,
                                           @RequestPart(value = "profilePhoto" , required = false) MultipartFile profilePhoto) throws IOException {
        return ok(authService.register(dtoUserDefIU , profilePhoto));
    }

    @PostMapping("/authenticate")
    @Override
    public RootEntity<JWT> authenticate(@RequestBody @Valid AuthRequest authRequest) {
        return ok(authService.authenticate(authRequest));
    }

    @PostMapping("/refreshToken")
    @Override
    public RootEntity<JWT> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        return ok(authService.refreshToken(request));
    }

    @GetMapping("/hello")
    public String hello(){
        return "Enes BABAAAAAAAAAAAAA";
    }
}
