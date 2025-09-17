package com.dulo.chat_platform.service.impl;

import com.dulo.chat_platform.entity.Like;
import com.dulo.chat_platform.entity.LikeId;
import com.dulo.chat_platform.entity.Post;
import com.dulo.chat_platform.entity.User;
import com.dulo.chat_platform.entity.enums.ErrorEnum;
import com.dulo.chat_platform.exception.AppException;
import com.dulo.chat_platform.repository.LikeRepository;
import com.dulo.chat_platform.repository.PostRepository;
import com.dulo.chat_platform.repository.UserRepository;
import com.dulo.chat_platform.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @Override
    public void handleLikePost(String email, int postId) {
        User user = userRepository.findByEmail(email);
        if(user == null) throw new AppException(ErrorEnum.USER_NOT_FOUND);
        Post post = postRepository.findById(postId).orElseThrow(() -> new AppException(ErrorEnum.POST_NOT_FOUND));

        Like likedPost = checkLiked(user, post);
        if(likedPost != null) {
            likeRepository.delete(likedPost);
        } else {
            LikeId likeId = new LikeId();
            likeId.setPostId(postId);
            likeId.setUserId(user.getId());
            likedPost = Like.builder()
                    .id(likeId)
                    .post(post)
                    .user(user)
                    .build();
            likeRepository.save(likedPost);
        }
    }

    @Override
    public int countLikesOfPost(int postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new AppException(ErrorEnum.POST_NOT_FOUND));
        return likeRepository.countLikeByPost(post);
    }

    public Like checkLiked(User user, Post post){
        return likeRepository.findAllByUserAndPost(user, post);
    }
}
