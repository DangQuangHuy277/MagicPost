package com.magicpost.app.magicPost.point.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GatheringPointResponse extends PointResponse{
    List<TransactionPointResponse> manageTransactionPoints;
}
