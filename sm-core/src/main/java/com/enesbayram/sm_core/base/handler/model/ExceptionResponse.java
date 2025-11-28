package com.enesbayram.sm_core.base.handler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse <T>{

    private String path;

    private String hostName;

    private Date errorTime;

    private T message;
}
