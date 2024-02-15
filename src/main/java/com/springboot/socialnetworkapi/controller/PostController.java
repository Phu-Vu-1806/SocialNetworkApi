package com.springboot.socialnetworkapi.controller;

import com.springboot.socialnetworkapi.entity.*;
import com.springboot.socialnetworkapi.payload.request.CommentRequest;
import com.springboot.socialnetworkapi.payload.request.PostRequest;
import com.springboot.socialnetworkapi.payload.response.ObjectResponse;
import com.springboot.socialnetworkapi.payload.response.PostResponse;
import com.springboot.socialnetworkapi.repository.FriendRepository;
import com.springboot.socialnetworkapi.repository.NotificationRepository;
import com.springboot.socialnetworkapi.repository.PostRepository;
import com.springboot.socialnetworkapi.repository.ProfileRepository;
import com.springboot.socialnetworkapi.service.impl.CommentServiceImpl;
import com.springboot.socialnetworkapi.service.impl.PostServiceImpl;
import com.springboot.socialnetworkapi.utils.ResponseMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable(name = "postId") Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new RuntimeException("Not found by id for post");
        }
        return ResponseEntity.ok().body(new ResponseMessage(post));
    }

    @GetMapping("/{location}")
    public ResponseEntity<?> getAllPostsFromProfile(@PathVariable(name = "location") String location) {
        Profile profile = profileRepository.findByLocation(location).get();
        List<Post> posts = profile.getPosts();
        List<PostResponse> postResponses = posts.stream().map(post -> modelMapper.map(post, PostResponse.class)).collect(Collectors.toList());

        return ResponseEntity.ok().body(new ObjectResponse(profile.getUser().getName(), profile.getLocation(), postResponses));
    }

    @PostMapping("/{location}/create")
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest,
                                        @PathVariable(name = "location") String location) {

        Profile profile = profileRepository.findByLocation(location).get();
        PostResponse postResponse = postService.createPost(postRequest, profile, null);

        List<Follow> follows = profile.getFollowers();
        Post post = postRepository.getNewPost(profile.getId());

        String mess = profile.getUser().getName() + " đã có một bài đăng mới";
        String url = "http://localhost:8080/post/" + post.getId();

        List<Notification> notifications = new ArrayList<>();
        for (Follow follow : follows) {
            Notification notification = new Notification();
            notification.setMessage(mess);
            notification.setFromId(profile);
            notification.setUrl(url);
            notification.setToId(follow.getFollowing());
            notifications.add(notification);
        }

//        notificationRepository.save(notification);
        notificationRepository.saveAll(notifications);

        return ResponseEntity.ok().body(new ResponseMessage("Success", postResponse));
    }

    @PostMapping("/{location}/update/{postId}")
    public ResponseEntity<?> updatePost(@RequestBody PostRequest postRequest,
                                        @PathVariable(name = "location") String location,
                                        @PathVariable(name = "postId") Long postId) {

        Profile profile = profileRepository.findByLocation(location).get();
        PostResponse postResponse = postService.updatePost(postRequest, postId, profile);

        return ResponseEntity.ok().body(new ResponseMessage("Success", postResponse));
    }

    @PostMapping("/{location}/delete/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable(name = "postId") Long postId,
                                        @PathVariable(name = "location") String location) {

        Profile profile = profileRepository.findByLocation(location).get();
        postService.deletePost(postId, profile);

        return ResponseEntity.ok().body(new ResponseMessage("Success"));
    }

    @GetMapping("/comment/{postId}")
    public ResponseEntity<?> getAllCommentFromPost(@PathVariable(name = "postId") Long postId) {
        Post post = postRepository.findById(postId).get();
        List<Comment> comments = post.getComments();

        return ResponseEntity.ok().body(new ResponseMessage("Success", comments));
    }

    @PostMapping("/{location}/createCmt/{postId}")
    public ResponseEntity<?> createComment(@PathVariable(name = "location") String location,
                                           @PathVariable(name = "postId") Long postId,
                                           @RequestBody CommentRequest commentRequest) {

        Profile profile = profileRepository.findByLocation(location).get();
        Post post = postRepository.findById(postId).get();

        Comment comment = commentService.createComment(commentRequest, post, profile);

        return ResponseEntity.ok().body(new ResponseMessage("Success", comment));
    }

    @PostMapping("/{location}/editCmt/{postId}/comment_id={commentId}")
    public ResponseEntity<?> editComment(@PathVariable(name = "location") String location,
                                         @PathVariable(name = "postId") Long postId,
                                         @PathVariable(name = "commentId") Long commentId,
                                         @RequestBody CommentRequest commentRequest) {

        Profile profile = profileRepository.findByLocation(location).get();

        Comment comment = commentService.editComment(commentRequest, postId, profile);

        return ResponseEntity.ok().body(new ResponseMessage("Success", comment));
    }

    @PostMapping("/deleteCmt/{postId}/comment_id={commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "postId") Long postId,
                                           @PathVariable(name = "commentId") Long commentId) {
        Post post = postRepository.findById(postId).get();
        commentService.deleteComment(commentId, post);

        return ResponseEntity.ok().body(new ResponseMessage("Success"));
    }
}
