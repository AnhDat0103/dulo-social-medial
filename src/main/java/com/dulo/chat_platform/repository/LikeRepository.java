package com.dulo.chat_platform.repository;

import com.dulo.chat_platform.entity.Like;
import com.dulo.chat_platform.entity.Post;
import com.dulo.chat_platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LikeRepository extends JpaRepository<Like,Integer> {
    Like findAllByUserAndPost(User user, Post post);

    int countLikeByPost(Post post);
}
