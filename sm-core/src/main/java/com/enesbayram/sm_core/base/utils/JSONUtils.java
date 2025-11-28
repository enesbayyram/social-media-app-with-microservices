package com.enesbayram.sm_core.base.utils;

import com.enesbayram.sm_core.base.enums.MessageType;
import com.enesbayram.sm_core.base.exception.BaseException;
import com.enesbayram.sm_core.base.exception.ErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;
import org.springframework.boot.SpringApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@UtilityClass
public class JSONUtils {

    public static String toJSON(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try{
            return objectMapper.writeValueAsString(object);
        }catch(Exception e){
           throw new BaseException(new ErrorMessage(MessageType.OBJECT_TO_JSON_CONVERSION_EXCEPTION , ""));
        }
    }

    public static void main(String[] args){
        System.out.println(new BCryptPasswordEncoder().encode("1"));
    }
}
