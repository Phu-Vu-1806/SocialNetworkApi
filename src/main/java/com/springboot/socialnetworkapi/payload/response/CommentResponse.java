package com.springboot.socialnetworkapi.payload.response;

import com.springboot.socialnetworkapi.entity.Post;
import lombok.Data;

import java.util.Date;

@Data
public class CommentResponse {

//    private String commenter;
//
//    private String locationCommenter;

    private Long postId;

    private String text;

    private Date date;

    private Date updateDate;

}
