package com.springboot.socialnetworkapi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "friend_requests")
@Data
@NoArgsConstructor
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_friend_request")
    private Profile friendRequest;

    @ManyToOne
    @JoinColumn(name = "profile_friend_response")
    private Profile friendResponse;

    public FriendRequest(Profile friendRequest, Profile friendResponse) {
        this.friendRequest = friendRequest;
        this.friendResponse = friendResponse;
    }
}
