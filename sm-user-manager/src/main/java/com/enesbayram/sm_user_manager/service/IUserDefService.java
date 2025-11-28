package com.enesbayram.sm_user_manager.service;

import com.enesbayram.sm_core.base.dto.DtoUserDefIU;
import com.enesbayram.sm_core.base.model.UserDef;
import com.enesbayram.sm_core.base.model.base.RootEntity;
import com.enesbayram.sm_core.base.service.IBaseCrudService;
import com.enesbayram.sm_core.base.service.IBaseDbService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUserDefService extends IBaseCrudService<UserDef>, IBaseDbService<UserDef> {

    UserDef findByUsername(String username);

    UserDef findByUserId(String userDefId);

    UserDef saveUserDef(DtoUserDefIU dtoUserDefIU , MultipartFile profilePhoto) throws IOException;

    UserDef updateUserDef(String userId, DtoUserDefIU dtoUserDefIU);

    boolean deleteUser(String username);

}
