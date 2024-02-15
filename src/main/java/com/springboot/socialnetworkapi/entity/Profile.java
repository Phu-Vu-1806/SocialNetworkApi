package com.springboot.socialnetworkapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "profiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "website")
    private String website;

    @Column(name = "location")
    private String location;

    @Column(name = "join_date")
    private Date joinDate;

    @OneToMany(mappedBy = "profile")
    private List<Skill> skills = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private List<Experience> experiences = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private List<Education> educations = new ArrayList<>();

    @OneToOne(mappedBy = "profile")
    private Social social;

    @OneToMany(mappedBy = "friendRequest")
    private List<FriendRequest> friendsRequests = new ArrayList<>();

    @OneToMany(mappedBy = "friendResponse")
    private List<FriendRequest> friendsResponse = new ArrayList<>();

    @OneToMany(mappedBy = "friendConfirm")
    private List<Friend> friendsConfirm = new ArrayList<>();

    @OneToMany(mappedBy = "friendAdd")
    private List<Friend> friendsAdd = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "following")
    private List<Follow> followings = new ArrayList<>();

    @OneToMany(mappedBy = "follower")
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<Post> posts = new ArrayList<>();

    @OneToOne(mappedBy = "profile")
    private User user;

    @OneToMany(mappedBy = "profile")
    private List<MemberRequest> memberRequests = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private List<Manager> managers = new ArrayList<>();

    @OneToMany(mappedBy = "fromId")
    private List<Notification> fromId = new ArrayList<>();

    @OneToMany(mappedBy = "toId")
    private List<Notification> toId = new ArrayList<>();

}
