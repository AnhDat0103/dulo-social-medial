package com.dulo.chat_platform.service.impl;

import com.dulo.chat_platform.dto.response.FriendshipResponse;
import com.dulo.chat_platform.dto.response.UserResponse;
import com.dulo.chat_platform.entity.Friendship;
import com.dulo.chat_platform.entity.FriendshipId;
import com.dulo.chat_platform.entity.User;
import com.dulo.chat_platform.entity.enums.ErrorEnum;
import com.dulo.chat_platform.entity.enums.FriendshipStatus;
import com.dulo.chat_platform.exception.AppException;
import com.dulo.chat_platform.mapper.UserMapper;
import com.dulo.chat_platform.repository.FriendshipRepository;
import com.dulo.chat_platform.repository.UserRepository;
import com.dulo.chat_platform.service.FriendShipService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FriendShipServiceImpl implements FriendShipService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final UserMapper userMapper;

    @Override
    public void sendFriendRequest(String fromEmail, int  toUserId) {

        User formUser = userRepository.findByEmail(fromEmail);
        if(formUser == null) throw new AppException(ErrorEnum.USER_NOT_FOUND);
        User toUser = userRepository.findById(toUserId).orElseThrow(() -> new AppException(ErrorEnum.USER_NOT_FOUND));

        int rows = friendshipRepository.checkFriendRequest(formUser.getId(), toUser.getId()); //xet truong hop , da la ban, da bi block, da gui loi moi ket ban
        if(rows >= 1) throw new AppException(ErrorEnum.CAN_NOT_SEND_FRIEND_REQUEST);

        FriendshipId friendshipId = new FriendshipId();
        friendshipId.setFriendId(toUser.getId());

        friendshipId.setUserId(formUser.getId());
        Friendship friendship = Friendship.builder()
                .id(friendshipId)
                .user(formUser)
                .friend(toUser)
                .createdAt(LocalDateTime.now())
                .status(FriendshipStatus.PENDING)
                .build();

        friendshipRepository.save(friendship);
    }

    @Override
    public FriendshipResponse respondToFriendRequest(FriendshipId requestId, FriendshipStatus status) {
        Friendship friendship = friendshipRepository.findById(requestId).orElseThrow(
                () -> new AppException(ErrorEnum.FRIENDSHIP_IS_NOT_FOUND)
        );
        if(status.equals(FriendshipStatus.ACCEPTED)){
            friendship.setStatus(FriendshipStatus.ACCEPTED);
        }
        if(status.equals(FriendshipStatus.REJECTED) || status.equals(FriendshipStatus.CANCELED)){
            deleteFriendRequest(requestId);
        }
        if(status.equals(FriendshipStatus.BLOCKED)){
            friendship.setStatus(FriendshipStatus.BLOCKED);
        }
        friendshipRepository.save(friendship);

        return FriendshipResponse.builder()
                .formUserId(friendship.getUser().getId())
                .toUserId(friendship.getFriend().getId())
                .createdAt(friendship.getCreatedAt())
                .status(friendship.getStatus())
                .build();
    }

    @Override
    public Page<UserResponse> getListFriends(String email, int page, int size) {
        User user = userRepository.findByEmail(email);
        if(user == null) throw new AppException(ErrorEnum.USER_NOT_FOUND);
        Page<User> friends = friendshipRepository.findFriends(user.getId(), PageRequest.of(page, size));
        return friends.map(userMapper::toUserResponse);
    }

    @Override
    public boolean areFriends(String fromEmail, int friendId) {
        User fromUser = userRepository.findByEmail(fromEmail);
        if(fromUser == null) throw new AppException(ErrorEnum.USER_NOT_FOUND);
        User toUser = userRepository.findById(friendId).orElseThrow(() -> new AppException(ErrorEnum.USER_NOT_FOUND));

        return friendshipRepository.existsByFriendship(fromUser.getId(), toUser.getId()) >= 1;
    }

    @Override
    public Page<FriendshipResponse> getReceivedRequests(String email, int page, int size) {
        User currentUser = userRepository.findByEmail(email);
        if(currentUser == null) throw new AppException(ErrorEnum.USER_NOT_FOUND);
        Page<Friendship> friendships = friendshipRepository.findAllByFriendAndStatus(currentUser, FriendshipStatus.PENDING, PageRequest.of(page, size, Sort.by("createdAt").descending()));
        return friendships.map(f ->
            FriendshipResponse.builder()
                    .toUserId(currentUser.getId())
                    .formUserId(f.getUser().getId())
                    .status(f.getStatus())
                    .createdAt(f.getCreatedAt())
                    .build()
        );
    }

    public void deleteFriendRequest(FriendshipId requestId){
        Friendship friendship = friendshipRepository.findById(requestId).orElseThrow(
                () -> new AppException(ErrorEnum.FRIENDSHIP_IS_NOT_FOUND)
        );
        friendshipRepository.delete(friendship);
    }
}
