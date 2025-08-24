package com.dulo.chat_platform.repository;

import com.dulo.chat_platform.entity.PostAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostAttachmentRepository extends JpaRepository<PostAttachment,Integer> {
}
