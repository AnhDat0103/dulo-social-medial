package com.dulo.chat_platform.controller;

import com.dulo.chat_platform.entity.Role;
import com.dulo.chat_platform.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public List<Role> getRoles() {
        return roleService.findAll();
    }

    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.save(role);
    }

}
