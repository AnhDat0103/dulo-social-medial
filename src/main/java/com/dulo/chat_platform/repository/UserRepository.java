package com.dulo.chat_platform.repository;

import com.dulo.chat_platform.entity.Phone;
import com.dulo.chat_platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface UserRepository  extends JpaRepository<User,Integer> {

    User findByEmail(String email);

    boolean existsByEmail(String email);
}
