package com.magicpost.app.magicPost.user.staff;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("TRANSACTION_STAFF")
public class TransactionStaff extends Staff{
//    @ManyToOne
//    @JoinColumn(name = "transaction_point_id")
//    private TransactionPoint transactionPoint;
}

