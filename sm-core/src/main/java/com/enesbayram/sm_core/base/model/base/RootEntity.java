package com.enesbayram.sm_core.base.model.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RootEntity<T> {

    private boolean status;

    private T data;

    private String errorMessage;


    private static <T> RootEntity<T> build(boolean status, T data, String errorMessage) {
        RootEntity<T> rootEntity = new RootEntity<>();
        rootEntity.setStatus(status);
        rootEntity.setData(data);
        rootEntity.setErrorMessage(errorMessage);
        return rootEntity;
    }

    public static <T> RootEntity<T> ok(T data) {
        return build(true, data, null);
    }

    public static <T> RootEntity<T> error(String errorMessage) {
        return build(false, null, errorMessage);
    }
}
