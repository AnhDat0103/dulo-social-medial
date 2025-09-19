package com.dulo.chat_platform.repository;

import com.dulo.chat_platform.entity.Friendship;
import com.dulo.chat_platform.entity.FriendshipId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, FriendshipId> {

    @Query(
            value = "SELECT COUNT(*) FROM friendships fs where fs.status = 'ACCEPTED' AND (fs.user_id = ?1 AND fs.friend_id = ?2) OR (fs.user_id = ?2 AND fs.friend_id = ?1)" ,
            nativeQuery = true
    )
    int existsByFriendship(int id, int id1);

    @Query(
            value = "SELECT count(*) from friendships fs where fs.status in ('ACCEPTED', 'BLOCKED', 'PENDING') AND (fs.user_id = ?1 AND fs.friend_id = ?2) OR (fs.user_id = ?2 AND fs.friend_id = ?1) ",
            nativeQuery = true
    )
    int checkFriendRequest(int id, int id1);
}
