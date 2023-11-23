package com.magicpost.app.magicPost.user.entity.staff;

import com.magicpost.app.magicPost.point.entity.GatheringPoint;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("GATHERING_STAFF")
public class GatheringStaff extends Staff{
    @ManyToOne
    @JoinColumn(name = "gathering_point_id")
    @Hidden
    private GatheringPoint gatheringPoint;
}
