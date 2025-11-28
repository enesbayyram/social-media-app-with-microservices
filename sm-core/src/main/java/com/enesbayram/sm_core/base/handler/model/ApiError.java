package com.enesbayram.sm_core.base.handler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError<T>{

    private Integer status;

    private ExceptionResponse<T> exception;
}
