package com.springboot.socialnetworkapi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "follows")
@Data
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "following")
    private Profile following;

    @ManyToOne
    @JoinColumn(name = "follower")
    private Profile follower;
}
