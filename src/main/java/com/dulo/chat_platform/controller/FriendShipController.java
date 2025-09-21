package com.dulo.chat_platform.controller;

import com.dulo.chat_platform.dto.response.ApiResponse;
import com.dulo.chat_platform.dto.response.FriendshipResponse;
import com.dulo.chat_platform.dto.response.UserResponse;
import com.dulo.chat_platform.entity.FriendshipId;
import com.dulo.chat_platform.entity.User;
import com.dulo.chat_platform.entity.enums.ErrorEnum;
import com.dulo.chat_platform.entity.enums.FriendshipStatus;
import com.dulo.chat_platform.exception.AppException;
import com.dulo.chat_platform.repository.UserRepository;
import com.dulo.chat_platform.service.FriendShipService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friendShips")
@RequiredArgsConstructor
public class FriendShipController {

    private final FriendShipService friendShipService;
    private final UserRepository userRepository;

    @PostMapping("/{toUserId}")
    public ApiResponse<Void> sendFriendRequest(Authentication authentication, @PathVariable int toUserId){
        String fromEmail = authentication.getName();
        friendShipService.sendFriendRequest(fromEmail, toUserId);
        return ApiResponse.<Void>builder()
                .code("200")
                .message("send friend request is successfully.")
                .build();
    }

    @PatchMapping("/{fromUserId}")
    public ApiResponse<FriendshipResponse> responseFriendRequest(Authentication authentication, @PathVariable int fromUserId,
                                                                 @RequestParam String status){
        User currentUser = userRepository.findByEmail(authentication.getName());
        if(currentUser == null) throw new AppException(ErrorEnum.USER_NOT_FOUND);
        FriendshipId friendshipId = new FriendshipId();

        friendshipId.setFriendId(currentUser.getId()); // vi nguoi xu ly friend request la nguoi dduocj nhan loi moi ket ban
        friendshipId.setUserId(fromUserId);

        FriendshipResponse friendshipResponse = friendShipService.respondToFriendRequest(friendshipId, getFriendShipStatus(status));
        return ApiResponse.<FriendshipResponse>builder()
                .code("200")
                .message("Processing response friend request is completed.")
                .data(friendshipResponse)
                .build();
     }

     @GetMapping
     public ApiResponse<PagedModel<UserResponse>> getListFriends(Authentication authentication,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size){
         String email = authentication.getName();
         Page<UserResponse> friends = friendShipService.getListFriends(email, page, size);
         return ApiResponse.<PagedModel<UserResponse>>builder()
                 .code("200")
                 .message("Get friend list")
                 .data(new PagedModel<>(friends))
                 .build();
     }

     @GetMapping("check-friendship/{friendId:\\d+}")
     public ApiResponse<Boolean> checkFriendShip(Authentication authentication, @PathVariable int friendId){
        String currentUserEmail = authentication.getName();
        return ApiResponse.<Boolean>builder()
                .code("200")
                .message("Check relationship is completed")
                .data(friendShipService.areFriends(currentUserEmail, friendId))
                .build();
     }


     public FriendshipStatus getFriendShipStatus(String status){
        return FriendshipStatus.valueOf(status.toUpperCase());
     }


     @GetMapping("/pending-friend-request")
    public ApiResponse<PagedModel<FriendshipResponse>> getPendingFriendRequest(Authentication authentication,
                                                                               @RequestParam(defaultValue = "0") int page,
                                                                               @RequestParam(defaultValue = "5") int size){
        String currentUser = authentication.getName();
        return ApiResponse.<PagedModel<FriendshipResponse>>builder()
                .code("200")
                .message("Get list pending friend request")
                .data(new PagedModel<>(friendShipService.getReceivedRequests(currentUser, page, size)))
                .build();
     }
}
