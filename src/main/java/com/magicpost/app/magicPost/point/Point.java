package com.magicpost.app.magicPost.point;


import com.magicpost.app.magicPost.address.Address;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Point {
    @Id
    @GeneratedValue
    private Long id;
    private Long totalReceiveOrders;
    private Long totalSendOrders;
    @Embedded
    private Address address;

    private String phone;
    private String email;

    private Integer zipcode;
}