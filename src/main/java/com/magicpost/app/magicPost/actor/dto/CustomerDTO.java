package com.magicpost.app.magicPost.actor.dto;

import com.magicpost.app.magicPost.address.dto.AddressDTO;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CustomerDTO {
    @Hidden
    private UUID id;
    private String name;
    private String phone;
    @NotNull
    private AddressDTO address;
}
