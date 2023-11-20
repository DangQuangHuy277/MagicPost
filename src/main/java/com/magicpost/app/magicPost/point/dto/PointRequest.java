package com.magicpost.app.magicPost.point.dto;

import com.magicpost.app.magicPost.address.dto.AddressDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PointRequest {
    @NotNull
    private String name;
    private AddressDTO address;
    private String phone;
    private String email;
}
