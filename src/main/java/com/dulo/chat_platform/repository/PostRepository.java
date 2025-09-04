package com.dulo.chat_platform.repository;

import com.dulo.chat_platform.entity.Post;
import com.dulo.chat_platform.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    Page<Post> findAllByUserAndIsDeleted(User user, Boolean isDeleted, Pageable pageable);

    @Query(
            value= "SELECT p.* FROM posts p where p.user_id = ?1 AND is_deleted = 0 AND scope = 'PUBLIC' ORDER BY created_at DESC;",
            countQuery = "SELECT COUNT(*) FROM posts p where p.user_id = ?1 AND is_deleted = 0 AND scope = 'PUBLIC' "
            ,nativeQuery = true
    )
    Page<Post> findAllByUser(int  userId, Pageable pageable);

    @Query(
            value = "SELECT * FROM posts p where p.user_id = ?1 AND p.is_deleted IS FALSE AND p.scope IN ('PUBLIC','FRIEND_ONLY') ORDER BY p.created_at DESC;",
            countQuery = "SELECT count(*) FROM posts p where p.user_id = ?1 AND p.is_deleted IS FALSE AND p.scope IN ('PUBLIC','FRIEND_ONLY')",
            nativeQuery = true
    )
    Page<Post> findAllWithFriendShipIsTrue(int friendId, Pageable Pageable);

    Page<Post> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);
}
