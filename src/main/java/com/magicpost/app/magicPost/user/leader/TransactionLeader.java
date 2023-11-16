package com.magicpost.app.magicPost.user.leader;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TRANSACTION_LEADER")
public class TransactionLeader extends Leader{
}
