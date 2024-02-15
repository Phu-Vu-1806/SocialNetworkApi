//package com.springboot.socialnetworkapi.entity;
//
//import lombok.Data;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Entity
//@Table(name = "posts_group")
//@Data
//public class PostGroup {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String text;
//
//    private Date date;
//
//    @Column(name = "update_date")
//    private Date updateDate;
//
//    @ManyToOne
//    @JoinColumn(name = "profile_id")
//    private Profile owner;
//
//    @ManyToOne
//    @JoinColumn(name = "group_id")
//    private Group group;
//
//    @OneToMany(mappedBy = "post")
//    private List<Comment> comments = new ArrayList<>();
//}
