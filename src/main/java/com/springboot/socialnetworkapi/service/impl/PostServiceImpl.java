package com.springboot.socialnetworkapi.service.impl;

import com.springboot.socialnetworkapi.entity.Group;
import com.springboot.socialnetworkapi.entity.Post;
import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.entity.User;
import com.springboot.socialnetworkapi.payload.request.PostRequest;
import com.springboot.socialnetworkapi.payload.response.PostResponse;
import com.springboot.socialnetworkapi.repository.PostRepository;
import com.springboot.socialnetworkapi.service.inf.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostResponse createPost(PostRequest postRequest, Profile profile, Group group) {
        List<Post> posts = profile.getPosts();
        Post post = modelMapper.map(postRequest, Post.class);
        post.setDate(new Date());
        post.setOwner(profile);
        post.setGroup(group);
        posts.add(post);

        PostResponse postResponse = modelMapper.map(post, PostResponse.class);
//        postResponse.setPoster(profile.getUser().getName());
//        postResponse.setLocationPoster(profile.getLocation());

        postRepository.save(post);
        return postResponse;
    }

    @Override
    public PostResponse updatePost(PostRequest postRequest, Long postId, Profile profile) {
        List<Post> posts = profile.getPosts();
        boolean check = false;
        for (Post post : posts){
            if(post.getId() == postId){
                check = true;
                post.setText(postRequest.getText());
                post.setUpdateDate(new Date());
                postRepository.save(post);

                PostResponse postResponse = modelMapper.map(post, PostResponse.class);
//                postResponse.setPoster(profile.getUser().getName());
//                postResponse.setLocationPoster(profile.getLocation());
                return postResponse;
            }
        }
        if(!check){
            throw new RuntimeException("Not found id for post");
        }
        return null;
    }

    @Override
    public void deletePost(Long postId, Profile profile) {
        List<Post> posts = profile.getPosts();
        boolean check = false;
        for (Post post : posts){
            if(post.getId() == postId){
                check = true;
                postRepository.delete(post);
            }
        }
        if(!check){
            throw new RuntimeException("Not found id for post");
        }
    }

    @Override
    public List<Post> getAllPostFromGroup(Group group, Integer pageNo, Integer pageSize, String sortBy){

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Post> posts = postRepository.findAllByGroup(group, pageable);
        if (posts.hasContent()) {
            return posts.getContent();
        } else {
            return new ArrayList<Post>();
        }
    }
}
