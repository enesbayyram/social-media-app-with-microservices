package com.enesbayram.sm_post_manager.service.impl;

import com.enesbayram.sm_core.base.dto.DtoBase;
import com.enesbayram.sm_core.base.dto.DtoUserDef;
import com.enesbayram.sm_core.base.model.UserDef;
import com.enesbayram.sm_core.base.service.impl.BaseDbServiceImpl;
import com.enesbayram.sm_post_manager.repository.UserDefRepository;
import com.enesbayram.sm_post_manager.service.IUserDefService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDefServiceImpl extends BaseDbServiceImpl<UserDefRepository , UserDef> implements IUserDefService {

    @Value("${profiles.host.url}")
    private String url;

    @Override
    public Class<? extends DtoBase> getDTOClassForService() {
        return DtoUserDef.class;
    }

    @Override
    public <D extends DtoBase> D toDTO(UserDef entity) {
        DtoUserDef dtoUserDef =  super.toDTO(entity);
        dtoUserDef.setProfilePhoto(url+"/sm-user-manager/user/image/"+entity.getId());
        return (D)dtoUserDef;
    }
}
