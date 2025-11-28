package com.enesbayram.sm_security_manager.service.impl;

import com.enesbayram.sm_core.base.dto.DtoBase;
import com.enesbayram.sm_core.base.dto.DtoUserDef;
import com.enesbayram.sm_core.base.model.UserDef;
import com.enesbayram.sm_core.base.service.impl.BaseDbServiceImpl;
import com.enesbayram.sm_security_manager.repository.UserDefRepository;
import com.enesbayram.sm_security_manager.service.IUserDefService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDefServiceImpl extends BaseDbServiceImpl<UserDefRepository, UserDef> implements IUserDefService {

    @Override
    public UserDef findByUsername(String username) {
        return dao.findByUsername(username).orElse(null);

    }

    @Override
    public Class<? extends DtoBase> getDTOClassForService() {
        return DtoUserDef.class;
    }
}
