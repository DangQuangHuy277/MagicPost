package com.magicpost.app.magicPost.point.dto;

import com.magicpost.app.magicPost.user.entity.leader.TransactionLeader;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class TransactionPointResponse extends PointResponse {
    private Long GatheringPointId;
    private Long transactionLeaderId;
    private String transactionLeaderUsername;
}
