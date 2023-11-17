package com.magicpost.app.magicPost.user.leader;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("COMPANY_LEADER")
public class CompanyLeader extends Leader{
}
