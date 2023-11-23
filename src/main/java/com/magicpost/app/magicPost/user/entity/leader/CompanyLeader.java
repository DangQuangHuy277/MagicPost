package com.magicpost.app.magicPost.user.entity.leader;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("COMPANY_LEADER")
public class CompanyLeader extends Leader{
}
