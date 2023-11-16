package com.magicpost.app.magicPost.user.staff;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TRANSACTION_STAFF")
public class TransactionStaff extends Staff{
}
