package com.enesbayram.sm_client.feign;

import com.enesbayram.sm_core.base.model.UserDef;
import com.enesbayram.sm_core.base.model.base.RootEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-manager-client" , url = "${sm.microservices.sm-user-manager}")
@ConditionalOnProperty("sm.microservices.sm-user-manager")
public interface UserManagerClient {

    @GetMapping("/user/userDefId/{userDefId}")
    RootEntity<UserDef> findByUserId(@PathVariable(value = "userDefId") String userDefId);

}
