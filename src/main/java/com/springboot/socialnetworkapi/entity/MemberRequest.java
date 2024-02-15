package com.springboot.socialnetworkapi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "member_requests")
@Data
@NoArgsConstructor
public class MemberRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public MemberRequest(Date date, Profile profile, Group group) {
        this.date = date;
        this.profile = profile;
        this.group = group;
    }
}
