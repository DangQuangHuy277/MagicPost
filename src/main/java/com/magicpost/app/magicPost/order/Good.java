package com.magicpost.app.magicPost.order;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Good {
    private String name;
    private Double value;
    private String attachDocument;
    private Long quantity;
}
