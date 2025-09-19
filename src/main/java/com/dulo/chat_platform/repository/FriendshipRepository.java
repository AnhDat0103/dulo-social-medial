package com.dulo.chat_platform.repository;

import com.dulo.chat_platform.entity.Friendship;
import com.dulo.chat_platform.entity.FriendshipId;
import com.dulo.chat_platform.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Query(
            value = "select u.* from friendShips fs join users u on fs.user_id = u.id or fs.friend_id = u.id where u.id <> ?1 and u.status = 'ACTIVE'",
            countQuery = "SELECT COUNT(*) FROM friendships fs " +
                    "JOIN users u ON (fs.user_id = u.id OR fs.friend_id = u.id) " +
                    "WHERE u.id <> ?1 AND u.status = 'ACTIVE'",
            nativeQuery = true
    )
    Page<User> findFriends(int id, Pageable pageable);
}
