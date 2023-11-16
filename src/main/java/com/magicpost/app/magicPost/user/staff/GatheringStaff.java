package com.magicpost.app.magicPost.user.staff;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("GATHERING_STAFF")
public class GatheringStaff extends Staff{
//    @ManyToOne
//    @JoinColumn(name = "gathering_point_id")
//    private GatheringPoint gatheringPoint;
}
