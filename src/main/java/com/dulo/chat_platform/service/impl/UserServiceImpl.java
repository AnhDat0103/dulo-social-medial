package com.dulo.chat_platform.service.impl;

import com.dulo.chat_platform.dto.request.RegistrationRequest;
import com.dulo.chat_platform.dto.request.UserPatchRequest;
import com.dulo.chat_platform.dto.response.UserResponse;
import com.dulo.chat_platform.entity.Phone;
import com.dulo.chat_platform.entity.Role;
import com.dulo.chat_platform.entity.User;
import com.dulo.chat_platform.entity.enums.ErrorEnum;
import com.dulo.chat_platform.entity.enums.Provider;
import com.dulo.chat_platform.entity.enums.RoleName;
import com.dulo.chat_platform.entity.enums.UserStatus;
import com.dulo.chat_platform.exception.AppException;
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
             throw new AppException(ErrorEnum.EMAIL_ALREADY_EXISTS);
        }
        if(phoneRepository.existsByPhone(registrationRequest.getPhone())) {
            throw new AppException(ErrorEnum.PHONE_ALREADY_EXISTS);
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
            throw new AppException(ErrorEnum.EMAIL_VERIFICATION_SENDING_FAILED);
        }
        return userMapper.toUserResponse(user);

    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return userMapper.toUserResponse(userRepository.findByEmail(email));
    }

    @Override
    public UserResponse getUserById(int id) {
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorEnum.USER_NOT_FOUND)
        ));
    }

    @Override
    public UserResponse updateUserByPatch(String email, UserPatchRequest userPatchRequest) {

        User user = userRepository.findByEmail(email);
        if(user == null) throw new AppException(ErrorEnum.USER_NOT_FOUND);

        if(userPatchRequest.getFullName() != null){
            user.setFullName(userPatchRequest.getFullName());
        }
        if(userPatchRequest.getDob() != null){
            user.setDob(userPatchRequest.getDob());
        }
        if(userPatchRequest.getAvatar() != null){
            user.setAvatar(userPatchRequest.getAvatar());
        }
        if(userPatchRequest.getPhones() != null){
            addNewPhoneToCurrentPhones(user, userPatchRequest.getPhones());
        }
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public void deleteUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorEnum.USER_NOT_FOUND));

        user.setStatus(UserStatus.DELETED);
        user.setIsDeleted(Boolean.TRUE);
        userRepository.save(user);
    }

    @Override
    public void uploadAvatar(String email, String fileName) {
        User user =  userRepository.findByEmail(email);
        if(user == null) throw new AppException(ErrorEnum.USER_NOT_FOUND);
        if(!fileName.isEmpty() || !fileName.isBlank()) {
            user.setAvatar(fileName);
            userRepository.save(user);
        }
    }

    private void addNewPhoneToCurrentPhones(User user, Set<Phone> newPhones){
        Set<Phone> currentPhones = user.getPhones();

        for (Phone newPhone: newPhones){
            boolean existingPhone = currentPhones.stream().anyMatch(phone -> phone.getPhone().equals(newPhone.getPhone()));
            if(!existingPhone) {
                if(phoneRepository.existsByPhone(newPhone.getPhone())) {
                    throw new AppException(ErrorEnum.PHONE_ALREADY_EXISTS);
                }

                currentPhones.add(newPhone);
                newPhone.setUser(user);
            }
        }
    }
}
