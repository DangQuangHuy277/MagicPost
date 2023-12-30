package com.magicpost.app.magicPost.user.entity.leader;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.magicpost.app.magicPost.point.entity.GatheringPoint;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("GATHERING_LEADER")
public class GatheringLeader extends Leader{
    @OneToOne(mappedBy = "gatheringLeader",orphanRemoval = false)
    @Hidden
    @JsonBackReference
    private GatheringPoint gatheringPoint;
}
