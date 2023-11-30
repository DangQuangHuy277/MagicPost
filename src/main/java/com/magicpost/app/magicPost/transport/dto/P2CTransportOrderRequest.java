package com.magicpost.app.magicPost.transport.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class P2CTransportOrderRequest{
    List<UUID> expressOrderIdList;
    @Schema(description = "no need to specify when send from transaction point to gathering point")
    UUID shipperId;
}
