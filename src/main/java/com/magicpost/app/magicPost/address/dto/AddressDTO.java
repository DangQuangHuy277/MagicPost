package com.magicpost.app.magicPost.address.dto;

import com.magicpost.app.magicPost.address.entity.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressDTO {
    private String street;
    private String zipcode;
    private String commune;
    private String district;
    private String province;
}
