package com.dulo.chat_platform.repository;

import com.dulo.chat_platform.entity.Role;
import com.dulo.chat_platform.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(RoleName name);
}
