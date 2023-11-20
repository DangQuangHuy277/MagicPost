package com.magicpost.app.magicPost.point.entity;

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
    @OneToMany(/*mappedBy = "gatheringPoint",*/ orphanRemoval = true)
    @JoinColumn(name = "gathering_point_id")
    private Set<GatheringStaff> gatheringStaffs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "gatheringPoint", orphanRemoval = true)
    private Set<TransactionPoint> manageTransactionPoints = new LinkedHashSet<>();
}
