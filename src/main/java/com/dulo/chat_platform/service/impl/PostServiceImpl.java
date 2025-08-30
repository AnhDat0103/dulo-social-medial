package com.dulo.chat_platform.service.impl;

import com.dulo.chat_platform.dto.request.PostPatchRequest;
import com.dulo.chat_platform.dto.request.PostRequest;
import com.dulo.chat_platform.dto.response.PostResponse;
import com.dulo.chat_platform.entity.Post;
import com.dulo.chat_platform.entity.User;
import com.dulo.chat_platform.entity.enums.ErrorEnum;
import com.dulo.chat_platform.entity.enums.PostStatus;
import com.dulo.chat_platform.exception.AppException;
import com.dulo.chat_platform.mapper.PostMapper;
import com.dulo.chat_platform.repository.FriendshipRepository;
import com.dulo.chat_platform.repository.PostRepository;
import com.dulo.chat_platform.repository.UserRepository;
import com.dulo.chat_platform.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    @Override
    public PostResponse getPostById(int id) {
        return null;
    }

    @Override
    public Page<PostResponse> getAllPosts(int page, int size) {
        return postRepository.findAllByIsDeleted(false,PageRequest.of(page, size)).map(postMapper::toPostResponse);
    }

    @Override
    public Page<PostResponse> getMyPosts(String email, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        User user = userRepository.findByEmail(email);
        if(user == null) throw new AppException(ErrorEnum.USER_NOT_FOUND);

        return postRepository.findAllByUserAndIsDeleted(user,false, pageRequest).map(postMapper::toPostResponse);
    }

    @Override
    public Page<PostResponse> getFriendPostsByFriendIdAndScope(String viewerId, int friendId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        User viewer = userRepository.findByEmail(viewerId);
        if(viewer == null) throw new AppException(ErrorEnum.USER_NOT_FOUND);

        User friend = userRepository.findById(friendId).orElseThrow(() ->  new AppException(ErrorEnum.USER_NOT_FOUND));

        if(checkFriendShip(viewer, friend)){
            return postRepository.findAllWithFriendShipIsTrue(friend.getId(), pageRequest).map(postMapper::toPostResponse);
        }
        return postRepository.findAllByUser(friendId, pageRequest).map(postMapper::toPostResponse);
    }

    @Override
    public PostResponse createPost(PostRequest postRequest, String email) {
        User user = userRepository.findByEmail(email);
        if(user == null) throw new AppException(ErrorEnum.USER_NOT_FOUND);
        Post post = Post.builder()
                .content(postRequest.getContent())
                .scope(postRequest.getScope())
                .isDeleted(false)
                .status(PostStatus.ACCEPTED)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
        return postMapper.toPostResponse(postRepository.save(post));
    }

    @Override
    public PostResponse updatePost(String email, PostPatchRequest  postPatchRequest, int currentPostId) {
        return null;
    }

    @Override
    public void deletePost(String email, int postId) {

    }

    private boolean checkFriendShip(User user1, User user2){
        return friendshipRepository.existsByFriendship(user1.getId(), user2.getId()) >= 1;
    }
}
