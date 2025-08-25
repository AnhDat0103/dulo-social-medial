package com.dulo.chat_platform.exception;

import com.dulo.chat_platform.entity.enums.ErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class AppException extends RuntimeException {

    private String code;

    public AppException(ErrorEnum error) {
        super(error.getMessage());
        this.code = error.getCode();
    }
}
