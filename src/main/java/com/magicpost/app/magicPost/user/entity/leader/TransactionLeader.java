package com.magicpost.app.magicPost.user.entity.leader;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.magicpost.app.magicPost.point.entity.TransactionPoint;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("TRANSACTION_LEADER")
@Getter
@Setter
@NoArgsConstructor
public class TransactionLeader extends Leader{
    @OneToOne(mappedBy = "transactionLeader",orphanRemoval = true)
    @Hidden
    @JsonBackReference
    private TransactionPoint transactionPoint;

}
