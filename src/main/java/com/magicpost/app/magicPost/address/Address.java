package com.magicpost.app.magicPost.address;

import jakarta.persistence.Entity;

public class Address {
    private String street;
    private Commune commune;
    private District district;
    private Province province;
}
