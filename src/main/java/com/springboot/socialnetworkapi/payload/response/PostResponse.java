package com.springboot.socialnetworkapi.payload.response;

import com.springboot.socialnetworkapi.entity.Comment;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class PostResponse {
//    private String poster;
//
//    private String locationPoster;

    private String text;

    private Date date;

    private Date updateDate;

    private List<Comment> comments = new ArrayList<>();

}
