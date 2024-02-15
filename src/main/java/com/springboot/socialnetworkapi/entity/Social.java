package com.springboot.socialnetworkapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "socials")
@Data
public class Social {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String youtube;

    private String facebook;

    private String twitter;

    private String instagram;

    private String linkedin;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

}
