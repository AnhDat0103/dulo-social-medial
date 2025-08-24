package com.dulo.chat_platform.repository;

import com.dulo.chat_platform.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone,Integer> {

    boolean existsByPhone(String phone);
}
