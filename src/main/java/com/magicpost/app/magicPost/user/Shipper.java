package com.magicpost.app.magicPost.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Shipper{
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;
    private String email;
    private String phone;
    private Boolean isActive = true;
}

