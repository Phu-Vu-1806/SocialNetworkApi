package com.springboot.socialnetworkapi.service.inf;

import com.springboot.socialnetworkapi.entity.Group;
import com.springboot.socialnetworkapi.entity.Post;
import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.payload.request.PostRequest;
import com.springboot.socialnetworkapi.payload.response.PostResponse;

import java.util.List;

public interface PostService {
    PostResponse createPost(PostRequest postRequest, Profile profile, Group group);
    PostResponse updatePost(PostRequest postRequest, Long postId, Profile profile);
    void deletePost(Long postId, Profile profile);
    List<Post> getAllPostFromGroup(Group group, Integer pageNo, Integer pageSize, String sortBy);
}
