package com.dulo.chat_platform.repository;

import com.dulo.chat_platform.dto.response.PostAttachmentResponse;
import com.dulo.chat_platform.entity.Post;
import com.dulo.chat_platform.entity.PostAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostAttachmentRepository extends JpaRepository<PostAttachment,Integer> {
    List<PostAttachment> findAllByPost(Post post);
}
