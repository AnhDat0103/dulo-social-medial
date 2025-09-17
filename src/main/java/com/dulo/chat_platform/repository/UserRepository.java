package com.dulo.chat_platform.repository;

import com.dulo.chat_platform.entity.User;
import com.dulo.chat_platform.entity.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface UserRepository  extends JpaRepository<User,Integer> {

    User findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByStatus(UserStatus status);

    User findByEmailAndStatus(String email, UserStatus status);
}
