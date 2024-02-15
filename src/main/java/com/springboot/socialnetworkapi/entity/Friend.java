package com.springboot.socialnetworkapi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "friends")
@Data
@NoArgsConstructor
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "friend_confirm")
    private Profile friendConfirm;

    @ManyToOne
    @JoinColumn(name = "friend_add")
    private Profile friendAdd;

    public Friend(Profile friendConfirm, Profile friendAdd) {
        this.friendConfirm = friendConfirm;
        this.friendAdd = friendAdd;
    }
}
