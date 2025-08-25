package com.dulo.chat_platform.service.impl;

import com.dulo.chat_platform.dto.request.RegistrationRequest;
import com.dulo.chat_platform.dto.response.UserResponse;
import com.dulo.chat_platform.entity.Phone;
import com.dulo.chat_platform.entity.Role;
import com.dulo.chat_platform.entity.User;
import com.dulo.chat_platform.entity.enums.Provider;
import com.dulo.chat_platform.entity.enums.RoleName;
import com.dulo.chat_platform.entity.enums.UserStatus;
import com.dulo.chat_platform.mapper.UserMapper;
import com.dulo.chat_platform.repository.PhoneRepository;
import com.dulo.chat_platform.repository.RoleRepository;
import com.dulo.chat_platform.repository.UserRepository;
import com.dulo.chat_platform.service.EmailVerificationService;
import com.dulo.chat_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final EmailVerificationService emailVerificationService;

    @Override
    public UserResponse createUser(RegistrationRequest registrationRequest) {
        if(userRepository.existsByEmail(registrationRequest.getEmail())) {
             throw new IllegalArgumentException("Email already in use");
        }
        if(phoneRepository.existsByPhone(registrationRequest.getPhone())) {
            throw new IllegalArgumentException("Phone number already in use");
        }
        User user = userMapper.toUser(registrationRequest);
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setProvider(Provider.LOCAL_SYSTEM);
        user.setStatus(UserStatus.PENDING);
        user.setIsDeleted(false);
        user.setCreatedAt(LocalDateTime.now());

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(RoleName.USER));
        user.setRoles(roles);

        Set<Phone> phones = new HashSet<>();
        Phone phone = Phone.builder()
                .phone(registrationRequest.getPhone())
                .user(user)
                .build();
        phones.add(phone);
        user.setPhones(phones);

        userRepository.save(user);
        try {
            emailVerificationService.sendVerificationEmail(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send verification email", e);
        }
        return userMapper.toUserResponse(user);

    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return userMapper.toUserResponse(userRepository.findByEmail(email));
    }
}
