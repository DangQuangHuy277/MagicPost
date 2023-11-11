package com.magicpost.app.magicPost.good;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Good {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long weight;
    private Type type;

    enum Type{
        DOCUMENT,
        GOOD
    }
}
