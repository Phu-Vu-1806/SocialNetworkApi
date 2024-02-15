package com.springboot.socialnetworkapi.service.inf;

import com.springboot.socialnetworkapi.entity.Comment;
import com.springboot.socialnetworkapi.entity.Post;
import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.payload.request.CommentRequest;
import com.springboot.socialnetworkapi.payload.response.CommentResponse;

import java.util.List;

public interface CommentService {

    Comment createComment(CommentRequest commentRequest, Post post, Profile profile);
    Comment editComment(CommentRequest commentRequest, Long commentId, Profile profile);
    void deleteComment(Long commentId, Post post);
    List<CommentResponse> getCommentFromProfile(Profile profile);
}
