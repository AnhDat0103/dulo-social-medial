package com.dulo.chat_platform.service;

import com.dulo.chat_platform.dto.request.RegistrationRequest;
import com.dulo.chat_platform.dto.request.UserPatchRequest;
import com.dulo.chat_platform.dto.response.UserResponse;

public interface UserService {

    UserResponse createUser(RegistrationRequest registrationRequest);

    UserResponse getUserByEmail(String email);

    UserResponse getUserById(int id);

    UserResponse updateUserByPatch(String email, UserPatchRequest userPatchRequest);

    void deleteUserById(int id);

    void uploadAvatar(String email, String fileName);
}
