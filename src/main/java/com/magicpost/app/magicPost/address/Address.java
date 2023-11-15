package com.magicpost.app.magicPost.address;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address {
    private String street;
    @ManyToOne
    @JoinColumn(name = "commune_id")
    private Commune commune;
}
