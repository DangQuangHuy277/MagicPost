package com.magicpost.app.magicPost.point.dto;

import com.magicpost.app.magicPost.address.dto.AddressDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PointResponse {
    private Long id;
    private String name;
    private AddressDTO address;
    private String phone;
    private String email;
}
