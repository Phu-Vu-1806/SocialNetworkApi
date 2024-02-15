package com.springboot.socialnetworkapi.entity;

import com.springboot.socialnetworkapi.entity.enums.ERoleGroup;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "managers")
@Data
@NoArgsConstructor
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Enumerated(EnumType.STRING)
    private ERoleGroup role;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public Manager(Profile profile, ERoleGroup role, Date date, Group group) {
        this.profile = profile;
        this.role = role;
        this.date = date;
        this.group = group;
    }
}
