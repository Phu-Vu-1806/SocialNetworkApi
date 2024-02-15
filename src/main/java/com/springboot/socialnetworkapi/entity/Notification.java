package com.springboot.socialnetworkapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "notification")
@Data
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private String url;

    @ManyToOne
    @JoinColumn(name = "from_id")
    @JsonIgnore
    private Profile fromId;

    @ManyToOne
    @JoinColumn(name = "to_id")
    @JsonIgnore
    private Profile toId;
}
