package com.magicpost.app.magicPost.order.dto;

import com.magicpost.app.magicPost.address.dto.AddressDTO;
import com.magicpost.app.magicPost.address.entity.Address;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TrackingEventResponse {
    private String message;
    private LocalDateTime timestamp;
    private AddressDTO location;
}
