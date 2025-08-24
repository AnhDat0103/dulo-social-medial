package com.dulo.chat_platform.mapper;

import com.dulo.chat_platform.dto.request.RegistrationRequest;
import com.dulo.chat_platform.dto.response.UserResponse;
import com.dulo.chat_platform.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(RegistrationRequest registrationRequest);
    UserResponse toUserResponse(User user);
}
