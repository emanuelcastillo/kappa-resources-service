package com.kappa.resources.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<RolesEntity> roles;

    @Column(name = "enabled")
    private Boolean enabled;

}
