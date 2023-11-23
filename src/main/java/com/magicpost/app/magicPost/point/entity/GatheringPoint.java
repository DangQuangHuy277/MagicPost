package com.magicpost.app.magicPost.point.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.magicpost.app.magicPost.user.entity.leader.GatheringLeader;
import com.magicpost.app.magicPost.user.entity.staff.GatheringStaff;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class GatheringPoint extends Point {

    @OneToMany(mappedBy = "gatheringPoint", orphanRemoval = true)
    private Set<GatheringStaff> gatheringStaffs = new LinkedHashSet<>();


    @OneToMany(mappedBy = "gatheringPoint", orphanRemoval = true)
    private Set<TransactionPoint> manageTransactionPoints = new LinkedHashSet<>();

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "gathering_leader_id")
    @JsonManagedReference
    private GatheringLeader gatheringLeader = null;

}

