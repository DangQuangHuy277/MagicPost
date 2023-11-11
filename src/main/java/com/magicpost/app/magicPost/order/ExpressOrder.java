package com.magicpost.app.magicPost.order;

import com.magicpost.app.magicPost.good.Good;
import com.magicpost.app.magicPost.user.Customer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.util.Date;

@Entity
public class ExpressOrder {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Good good;
    private Customer sender;
    private Customer receiver;
    private String specialService;
    private Instructor senderInstructor;
    private String senderCommitment;
    private Date sendTime;
    private Price price;
    private CashOnDelivery cashOnDelivery;
    private String businessInstructions;
    private Status status;

    enum Status {

    }

    public enum Instructor {
        IMMEDIATE_RETURN,
        CALL_SENDER,
        CANCEL,
        RETURN_BEFORE_DATE,
        RETURN_WHEN_STORAGE_EXPIRES
    }

    public class Price {
        private Double mainTax;
        private Double subTax;
        private Double transportTax;
        private Double totalTax;
        private Double otherTax;
        private Double totalPrice;
    }

    public class CashOnDelivery{
        private Double cod;
        private Double other;
        private Double totalCOD;
    }
}


