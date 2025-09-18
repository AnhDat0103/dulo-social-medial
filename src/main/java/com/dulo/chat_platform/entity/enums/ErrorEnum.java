package com.dulo.chat_platform.entity.enums;

import lombok.Getter;

@Getter
public enum ErrorEnum {

    USER_NOT_FOUND("1000", "User not found"),
    INVALID_CREDENTIALS("1001", "Invalid credentials"),
    EMAIL_ALREADY_EXISTS("1002", "Email already exists"),
    INVALID_TOKEN("1003", "Invalid token"),
    TOKEN_EXPIRED("1004", "Token has expired"),
    INTERNAL_SERVER_ERROR("1005", "Internal server error"),
    EMAIL_NOT_VERIFIED("1006", "Email not verified"),
    VERIFICATION_TOKEN_EXPIRED("1007", "Verification token has expired"),
    VERIFICATION_TOKEN_INVALID("1008", "Verification token is invalid"),
    INVALID_PASSWORD("1009", "Invalid password"),
    EMAIL_CERTIFICATED("1010", "Email already verified"),
    PHONE_ALREADY_EXISTS("1011", "Phone number already exists"),
    EMAIL_VERIFICATION_SENDING_FAILED("1012", "Failed to send verification email"),
    BAD_CREDENTIALS("1022","username or password is invalid."),
    DISABLED_EXCEPTION("1023", "The account has not activated yet."),
    ACCOUNT_LOCKED("1024", "Account is banned."),
    POST_NOT_FOUND("1025", "Post not exist!" ),
    ACCESSIONED_EXCEPTION("1026", "You are not allowed to update this post"),
    FILE_FAIL_TO_STORE("1027", "Failed to store file." ),
    ATTACHMENT_NOT_FOUND("1028", "Attachment is not found" ),
    PASSWORD_NOT_MATCH("1029","Password not match" );

    private final String code;
    private final String message;

    ErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
