package com.enesbayram.sm_security_manager.service.impl;


import com.enesbayram.sm_core.base.dto.DtoUserDef;
import com.enesbayram.sm_core.base.dto.DtoUserDefIU;
import com.enesbayram.sm_core.base.enums.MessageType;
import com.enesbayram.sm_core.base.exception.BaseException;
import com.enesbayram.sm_core.base.exception.ErrorMessage;
import com.enesbayram.sm_core.base.model.RefreshToken;
import com.enesbayram.sm_core.base.model.UserDef;
import com.enesbayram.sm_core.base.utils.DateUtils;
import com.enesbayram.sm_security_manager.api.model.AuthRequest;
import com.enesbayram.sm_security_manager.api.model.RefreshTokenRequest;
import com.enesbayram.sm_security_manager.service.IAuthService;
import com.enesbayram.sm_security_manager.service.IRefreshTokenService;
import com.enesbayram.sm_security_manager.service.IUserDefService;
import com.enesbayram.sm_security_manager.service.model.JWT;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final IUserDefService userDefService;

    private final IRefreshTokenService refreshTokenService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JWTService jwtService;

    private final AuthenticationProvider authenticationProvider;

    private JWT createJwtToken(UserDef userDef){
        JWT jwt = new JWT();

        String accessToken = jwtService.generateToken(userDef);
        RefreshToken refreshToken = refreshTokenService.saveRefreshToken(userDef);

        jwt.setAccessToken(accessToken);
        jwt.setAccessTokenExpired(DateUtils.convertDateToLocalDateTime(jwtService.exportToken(accessToken , Claims::getExpiration)));
        jwt.setRefreshToken(refreshToken.getRefreshToken());
        jwt.setRefreshTokenExpired(refreshToken.getExpireIn());

        log.info("JWT token : " + accessToken);
        return jwt;
    }

    @Override
    public JWT authenticate(AuthRequest authRequest) {
        try{
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername() , authRequest.getPassword());
            authenticationProvider.authenticate(authentication);

            UserDef userDef = userDefService.findByUsername(authRequest.getUsername());
            if(userDef==null){
                throw new BaseException(new ErrorMessage(MessageType.USER_NOT_FOUND , authRequest.getUsername()));
            }
            return createJwtToken(userDef);
        }catch(Exception e){
            throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID_EXCEPTION,""));
        }
    }

    @Override
    public DtoUserDef register(DtoUserDefIU dtoUserDefIU , MultipartFile profilePhoto) throws IOException {
        UserDef userDef = userDefService.findByUsername(dtoUserDefIU.getUsername());
        if (userDef != null) {
            throw new BaseException(new ErrorMessage(MessageType.USER_ALREADY_EXIST, dtoUserDefIU.getUsername()));
        }

        UserDef dbUserDef = userDefService.toDaoForInsert(dtoUserDefIU);
        dbUserDef.setPassword(bCryptPasswordEncoder.encode(dtoUserDefIU.getPassword()));
        dbUserDef.setProfilePhoto(profilePhoto.getBytes());

        return userDefService.toDTO(userDefService.save(dbUserDef));
    }


    @Override
    public JWT refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenService.findByRefreshToken(request.getRefreshToken());
        if(refreshToken==null){
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_NOT_FOUND , request.getRefreshToken()));
        }
        boolean refreshTokenValid = refreshTokenService.isRefreshTokenValid(refreshToken);
        if(!refreshTokenValid){
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_IS_EXPIRED , request.getRefreshToken()));
        }
        return createJwtToken(refreshToken.getUserDef());
    }


}
