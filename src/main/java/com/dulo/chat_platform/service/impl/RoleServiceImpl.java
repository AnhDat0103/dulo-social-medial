package com.dulo.chat_platform.service.impl;

import com.dulo.chat_platform.entity.Role;
import com.dulo.chat_platform.repository.RoleRepository;
import com.dulo.chat_platform.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role save(Role role) {
        if (role != null && role.getName() != null) {
            return roleRepository.save(role);
        } else {
            throw new IllegalArgumentException("Role or Role name cannot be null");
        }
    }
}
