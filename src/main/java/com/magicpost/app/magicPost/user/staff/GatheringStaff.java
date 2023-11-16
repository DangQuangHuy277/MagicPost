package com.magicpost.app.magicPost.user.staff;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GATHERING_STAFF")
public class GatheringStaff extends Staff{
}
