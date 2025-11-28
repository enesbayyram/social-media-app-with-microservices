package com.enesbayram.sm_user_manager.controller;

import com.enesbayram.sm_core.base.dto.DtoUserDef;
import com.enesbayram.sm_core.base.dto.DtoUserDefIU;
import com.enesbayram.sm_core.base.model.UserDef;
import com.enesbayram.sm_core.base.model.base.RootEntity;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IRestUserDefController {

    RootEntity<DtoUserDef> findByUsername(String username);

    ResponseEntity<byte[]> getUserProfilePhoto(String userDefId);

    RootEntity<UserDef> findByUserId(String userDefId);

    RootEntity<DtoUserDef> saveUserDef(DtoUserDefIU dtoUserDefIU,MultipartFile profilePhoto) throws IOException;

    RootEntity<DtoUserDef> updateUserDef(String userId, DtoUserDefIU dtoUserDefIU);

    RootEntity<Boolean> deleteUser(String username);
}
