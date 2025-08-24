package com.dulo.chat_platform.repository;

import com.dulo.chat_platform.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship,Integer> {
}
