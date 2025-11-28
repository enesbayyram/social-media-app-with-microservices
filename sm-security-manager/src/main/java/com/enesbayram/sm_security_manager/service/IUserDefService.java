package com.enesbayram.sm_security_manager.service;

import com.enesbayram.sm_core.base.model.UserDef;
import com.enesbayram.sm_core.base.service.IBaseCrudService;
import com.enesbayram.sm_core.base.service.IBaseDbService;

public interface IUserDefService extends IBaseDbService<UserDef>  , IBaseCrudService<UserDef> {

    public UserDef findByUsername(String username);

}
