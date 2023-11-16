package com.magicpost.app.magicPost.user.leader;

import com.magicpost.app.magicPost.point.GatheringPoint;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("GATHERING_LEADER")
public class GatheringLeader extends Leader{
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "gathering_point_id")
    private GatheringPoint gatheringPoint;
}
