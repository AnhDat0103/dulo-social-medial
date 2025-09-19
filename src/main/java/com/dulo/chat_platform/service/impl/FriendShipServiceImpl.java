package com.dulo.chat_platform.service.impl;

import com.dulo.chat_platform.dto.response.FriendshipResponse;
import com.dulo.chat_platform.entity.Friendship;
import com.dulo.chat_platform.entity.FriendshipId;
import com.dulo.chat_platform.entity.User;
import com.dulo.chat_platform.entity.enums.ErrorEnum;
import com.dulo.chat_platform.entity.enums.FriendshipStatus;
import com.dulo.chat_platform.exception.AppException;
import com.dulo.chat_platform.repository.FriendshipRepository;
import com.dulo.chat_platform.repository.UserRepository;
import com.dulo.chat_platform.service.FriendShipService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FriendShipServiceImpl implements FriendShipService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

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
    public Page<User> getListFriends(String email, int page, int size) {
        return null;
    }

    @Override
    public boolean areFriends(String fromEmail, String toEmail) {
        return false;
    }

    @Override
    public Page<Friendship> getReceivedRequests(String email, int page, int size) {
        return null;
    }

    @Override
    public Page<Friendship> getSentRequests(String email, int page, int size) {
        return null;
    }

    public void deleteFriendRequest(FriendshipId requestId){
        Friendship friendship = friendshipRepository.findById(requestId).orElseThrow(
                () -> new AppException(ErrorEnum.FRIENDSHIP_IS_NOT_FOUND)
        );
        friendshipRepository.delete(friendship);
    }
}
