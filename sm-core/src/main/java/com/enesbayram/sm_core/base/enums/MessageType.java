package com.enesbayram.sm_core.base.enums;

import lombok.Getter;

@Getter
public enum MessageType {

    NO_RECORD_EXIST("1001" , "Kayıt bulunamadı."),
    USER_NOT_FOUND("1002" , "Kullanıcı bulunamadı"),
    USER_ALREADY_EXIST("1003" , "Kullanıcı zaten sistemde kayıtlı"),
    USERNAME_OR_PASSWORD_INVALID_EXCEPTION("1004", "Kullanıcı adı veya şifre geçersizdir"),
    REFRESH_TOKEN_NOT_FOUND("1005" , "Refresh Token bulunamadı"),
    REFRESH_TOKEN_IS_EXPIRED("1006" , "RefreshToken geçerlilik süresi dolmuştur"),
    OBJECT_TO_JSON_CONVERSION_EXCEPTION("1007" , "Objeden JSON 'a çevirirken hata oluştu")
    ;

      final String code;
      final String message;

     MessageType(String code , String message){
       this.code = code ;
       this.message = message;
    }


}
