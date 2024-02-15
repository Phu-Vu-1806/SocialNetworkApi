package com.springboot.socialnetworkapi.service.impl;

import com.springboot.socialnetworkapi.entity.Comment;
import com.springboot.socialnetworkapi.entity.Post;
import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.payload.request.CommentRequest;
import com.springboot.socialnetworkapi.payload.response.CommentResponse;
import com.springboot.socialnetworkapi.repository.CommentRepository;
import com.springboot.socialnetworkapi.repository.PostRepository;
import com.springboot.socialnetworkapi.service.inf.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Comment createComment(CommentRequest commentRequest, Post post, Profile profile) {
        List<Comment> comments = profile.getComments();

        Comment comment = new Comment();
        comment.setText(commentRequest.getText());
        comment.setPost(post);
        comment.setProfile(profile);
        comment.setDate(new Date());

        comments.add(comment);

        commentRepository.save(comment);

        return comment;
    }

    @Override
    public Comment editComment(CommentRequest commentRequest, Long commentId, Profile profile) {
        List<Comment> comments = profile.getComments();
        boolean check = false;
        for (Comment comment : comments) {
            if (comment.getId() == commentId) {
                check = true;
                comment.setText(commentRequest.getText());
                comment.setUpdateDate(new Date());
                commentRepository.save(comment);
                return comment;
            }
        }
        if (!check) {
            throw new RuntimeException("not found id for comment");
        }
        return null;
    }

    @Override
    public void deleteComment(Long commentId, Post post) {

        List<Comment> comments = post.getComments();
        boolean check = false;
        for (Comment comment : comments) {
            if (comment.getId() == commentId) {
                check = true;
                commentRepository.delete(comment);
            }
        }
        if (!check) {
            throw new RuntimeException("not found id for comment");
        }
    }

    @Override
    public List<CommentResponse> getCommentFromProfile(Profile profile) {
        List<Comment> comments = profile.getComments();
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments){
            CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);

//            commentResponse.setCommenter(profile.getUser().getName());
//            commentResponse.setLocationCommenter(profile.getLocation());
            commentResponse.setPostId(comment.getPost().getId());

            commentResponses.add(commentResponse);
        }

        return commentResponses;
    }
}
