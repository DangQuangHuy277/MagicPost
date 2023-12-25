package com.magicpost.app.magicPost.point.dto;

import com.magicpost.app.magicPost.user.dto.UserResponse;
import com.magicpost.app.magicPost.user.entity.leader.GatheringLeader;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GatheringPointResponse extends PointResponse{
//    List<TransactionPointResponse> manageTransactionPoints;
    UserResponse gatheringLeader;
}
