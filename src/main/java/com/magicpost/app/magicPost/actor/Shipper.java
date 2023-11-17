package com.magicpost.app.magicPost.actor;

import com.magicpost.app.magicPost.point.TransactionPoint;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Shipper{
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;
    private String email;
    @NotNull
    private String phone;
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "transaction_point_id")
    private TransactionPoint transactionPoint;

}

