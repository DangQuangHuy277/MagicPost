package com.magicpost.app.magicPost.point.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.magicpost.app.magicPost.actor.Shipper;
import com.magicpost.app.magicPost.point.entity.GatheringPoint;
import com.magicpost.app.magicPost.point.entity.Point;
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

    @ManyToOne
    @JoinColumn(name = "gathering_point_id")
    private GatheringPoint gatheringPoint;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "transaction_leader_id")
    @JsonManagedReference
    private TransactionLeader transactionLeader = null;

    @OneToMany(mappedBy = "transactionPoint", orphanRemoval = true)
    private Set<Shipper> shipperSet = new LinkedHashSet<>();

    @OneToMany(mappedBy = "transactionPoint", orphanRemoval = true)
    @JsonManagedReference
    private Set<TransactionStaff> transactionStaffs = new LinkedHashSet<>();

}
