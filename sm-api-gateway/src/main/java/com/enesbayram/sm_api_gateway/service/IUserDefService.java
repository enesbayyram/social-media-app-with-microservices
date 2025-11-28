package com.enesbayram.sm_api_gateway.service;

import com.enesbayram.sm_api_gateway.model.UserDef;

public interface IUserDefService {

    public UserDef findByUsername(String username);
}
