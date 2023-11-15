package com.magicpost.app.magicPost.user;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;

public class User {
    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String email;
    private Integer phone;
    private Role role;
}
