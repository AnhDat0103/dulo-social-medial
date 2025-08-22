package com.dulo.chat_platform.service;

import com.dulo.chat_platform.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    Role save(Role role);
}
