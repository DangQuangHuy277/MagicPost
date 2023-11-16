package com.magicpost.app.magicPost.user.leader;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GATHERING_LEADER")
public class GatheringLeader extends Leader{
}
