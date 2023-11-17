package com.magicpost.app.magicPost.order;

import jakarta.persistence.*;

@Entity
public class Good {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long weight;
    @Enumerated(EnumType.STRING)
    private Type type;

    enum Type{
        DOCUMENT,
        GOOD
    }
}
