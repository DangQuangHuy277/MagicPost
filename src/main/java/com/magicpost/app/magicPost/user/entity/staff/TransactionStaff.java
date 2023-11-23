package com.magicpost.app.magicPost.user.entity.staff;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.magicpost.app.magicPost.point.entity.TransactionPoint;
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
@DiscriminatorValue("TRANSACTION_STAFF")
public class TransactionStaff extends Staff{
    @ManyToOne
    @JoinColumn(name = "transaction_point_id")
    @Hidden
    @JsonBackReference
    private TransactionPoint transactionPoint;
}

