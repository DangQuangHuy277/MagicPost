package com.magicpost.app.magicPost.point;


import com.magicpost.app.magicPost.address.Address;
import com.magicpost.app.magicPost.order.ExpressOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
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

    @ManyToMany
    @JoinTable(name = "point_inventory",
            joinColumns = @JoinColumn(name = "point_id"),
            inverseJoinColumns = @JoinColumn(name = "inventory_id"))
    private Set<ExpressOrder> inventory = new LinkedHashSet<>();

}