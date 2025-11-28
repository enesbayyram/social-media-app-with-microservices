package com.enesbayram.sm_user_manager.service.impl;

import com.enesbayram.sm_core.base.dto.DtoBase;
import com.enesbayram.sm_core.base.dto.DtoUserDef;
import com.enesbayram.sm_core.base.dto.DtoUserDefIU;
import com.enesbayram.sm_core.base.enums.MessageType;
import com.enesbayram.sm_core.base.exception.BaseException;
import com.enesbayram.sm_core.base.exception.ErrorMessage;
import com.enesbayram.sm_core.base.model.UserDef;
import com.enesbayram.sm_core.base.service.impl.BaseDbServiceImpl;
import com.enesbayram.sm_user_manager.repository.UserDefRepository;
import com.enesbayram.sm_user_manager.service.IUserDefService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDefServiceImpl extends BaseDbServiceImpl<UserDefRepository, UserDef> implements IUserDefService {

    @Value("${profiles.host.url}")
    private String url;

    @Override
    public Class<? extends DtoBase> getDTOClassForService() {
        return DtoUserDef.class;
    }

    @Override
    public UserDef findByUsername(String username) {
        Optional<UserDef> optional = dao.findByUsername(username);
        if (optional.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, username));
        }
        return optional.get();
    }

    @Override
    public UserDef findByUserId(String userDefId) {
        Optional<UserDef> optional = dao.findById(userDefId);
        if(optional.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, userDefId));
        }
        return optional.get();
    }

    @Override
    public UserDef saveUserDef(DtoUserDefIU dtoUserDefIU ,  MultipartFile profilePhoto) throws IOException {
        UserDef userDef = toDaoForInsert(dtoUserDefIU);
        userDef.setPassword(new BCryptPasswordEncoder().encode(userDef.getPassword()));
        if(profilePhoto!=null){
            userDef.setProfilePhoto(profilePhoto.getBytes());
        }
        return save(userDef);
    }

    @Override
    public UserDef updateUserDef(String userId, DtoUserDefIU dtoUserDefIU) {
        UserDef dbUserDef = findById(userId);
        if (dbUserDef == null) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, userId));
        }

        UserDef updateForUserDef = toDaoForUpdate(dtoUserDefIU, dbUserDef);
        updateForUserDef.setPassword(new BCryptPasswordEncoder().encode(updateForUserDef.getPassword()));
        return update(updateForUserDef);
    }

    @Override
    public boolean deleteUser(String username) {
        Optional<UserDef> optional = dao.findByUsername(username);
        if (optional.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, username));
        }
        delete(optional.get());
        return true;
    }

    @Override
    public <D extends DtoBase> D toDTO(UserDef entity) {
        DtoUserDef dtoUserDef = super.toDTO(entity);
        dtoUserDef.setProfilePhoto(url+"/sm-user-manager/user/image/" + dtoUserDef.getId());
        return (D) dtoUserDef;
    }
}
