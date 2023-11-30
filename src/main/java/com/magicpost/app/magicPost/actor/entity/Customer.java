package com.magicpost.app.magicPost.actor.entity;

import com.magicpost.app.magicPost.address.entity.Address;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    @Column(nullable = false, unique = true)
    private String phone;
    @NotNull
    private Address address;

    public Customer(String phone) {
        this.phone = phone;
    }
}