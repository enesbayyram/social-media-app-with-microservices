package com.enesbayram.sm_api_gateway.service.impl;

import com.enesbayram.sm_api_gateway.model.UserDef;
import com.enesbayram.sm_api_gateway.repository.UserDefRepository;
import com.enesbayram.sm_api_gateway.service.IUserDefService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDefServiceImpl implements IUserDefService {

    private final UserDefRepository userDefRepository;

    @Override
    public UserDef findByUsername(String username) {

        return userDefRepository.findByUsername(username).orElse(null);
    }
}
