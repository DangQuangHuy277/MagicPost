package com.magicpost.app.magicPost.point.entity;


import com.magicpost.app.magicPost.address.entity.Address;
import com.magicpost.app.magicPost.order.ExpressOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
public abstract class Point {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private Long totalReceiveOrders = 0L;
    private Long totalSendOrders = 0L;
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