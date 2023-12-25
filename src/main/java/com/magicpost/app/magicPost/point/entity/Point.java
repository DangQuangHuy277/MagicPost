package com.magicpost.app.magicPost.point.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.magicpost.app.magicPost.address.entity.Address;
import com.magicpost.app.magicPost.order.entity.ExpressOrder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
public abstract class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Setter(AccessLevel.NONE)
    private Long totalReceiveOrders = 0L;
    @Setter(AccessLevel.NONE)
    private Long totalSendOrders = 0L;
    @Embedded
    @JsonIgnore
    private Address address;

    private String phone;
    private String email;

//    @ManyToMany
//    @JoinTable(name = "point_inventory",
//            joinColumns = @JoinColumn(name = "point_id"),
//            inverseJoinColumns = @JoinColumn(name = "inventory_id"))
//    @JsonIgnore
//    private List<ExpressOrder> inventory = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "point_id")
    @MapKey(name = "id")
    private Map<UUID, ExpressOrder> inventory = new HashMap<>();

    public void addNumberSendOrders(int size) {
        totalSendOrders += size;
    }

    public void addNumberReceiverOrders(int size){
        totalReceiveOrders += size;
    }
}