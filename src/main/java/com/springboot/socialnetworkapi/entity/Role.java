package com.springboot.socialnetworkapi.entity;

import com.springboot.socialnetworkapi.entity.enums.ERole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ERole role;

    public Role(ERole role) {
        this.role = role;
    }
}
