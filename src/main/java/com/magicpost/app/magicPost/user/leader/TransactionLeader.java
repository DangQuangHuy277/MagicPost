package com.magicpost.app.magicPost.user.leader;

import com.magicpost.app.magicPost.point.TransactionPoint;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
@DiscriminatorValue("TRANSACTION_LEADER")
public class TransactionLeader extends Leader{
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "transaction_point_id")
    private TransactionPoint transactionPoint;

}
