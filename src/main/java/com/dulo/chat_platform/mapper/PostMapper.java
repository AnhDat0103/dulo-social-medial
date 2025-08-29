package com.dulo.chat_platform.mapper;

import com.dulo.chat_platform.dto.request.PostRequest;
import com.dulo.chat_platform.dto.response.PostResponse;
import com.dulo.chat_platform.entity.Post;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PostMapper {

    Post toPost(PostRequest postRequest);
    PostResponse toPostResponse(Post post);
}
