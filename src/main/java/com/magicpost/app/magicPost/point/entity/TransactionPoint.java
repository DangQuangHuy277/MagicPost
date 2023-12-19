package com.magicpost.app.magicPost.point.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.magicpost.app.magicPost.actor.entity.Shipper;
import com.magicpost.app.magicPost.user.entity.leader.TransactionLeader;
import com.magicpost.app.magicPost.user.entity.staff.TransactionStaff;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class TransactionPoint extends Point {
    private Long successOrders = 0L;
    private Long cancelOrders = 0L;

    @ManyToOne
    @JoinColumn(name = "gathering_point_id")
    private GatheringPoint gatheringPoint;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "transaction_leader_id")
    @JsonManagedReference
    private TransactionLeader transactionLeader = null;

    @OneToMany(mappedBy = "transactionPoint", orphanRemoval = true)
    private Set<Shipper> shippers = new LinkedHashSet<>();


    @OneToMany(mappedBy = "transactionPoint", orphanRemoval = true)
    @JsonManagedReference
    private Set<TransactionStaff> transactionStaffs = new LinkedHashSet<>();

    public void incrementSuccessOrders() {
        successOrders++;
    }

    public void incrementCancelOrders() {
        cancelOrders++;
    }

}
