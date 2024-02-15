package com.springboot.socialnetworkapi.payload.response;

import com.springboot.socialnetworkapi.entity.Comment;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class PostOfGroup {
    private String nameProfileUser;

    private String locationProfile;

    private String text;

    private Date date;

    private Date updateDate;

    private List<Comment> comments = new ArrayList<>();
}
