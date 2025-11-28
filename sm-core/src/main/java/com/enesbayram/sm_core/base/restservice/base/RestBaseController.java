package com.enesbayram.sm_core.base.restservice.base;

import com.enesbayram.sm_core.base.model.base.RootEntity;

public class RestBaseController {

    public <T> RootEntity<T> ok(T data) {
        return RootEntity.ok(data);
    }

    public <T> RootEntity<T> error(String errorMessage) {
        return RootEntity.error(errorMessage);
    }
}
