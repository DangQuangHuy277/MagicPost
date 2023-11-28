package com.magicpost.app.magicPost.actor;

import com.magicpost.app.magicPost.point.entity.TransactionPoint;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Shipper{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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

