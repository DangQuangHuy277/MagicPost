package com.magicpost.app.magicPost.point.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;



public class GatheringStatisticalResponse extends StatisticalResponse{

    public GatheringStatisticalResponse(Long pointId, String name, Long totalSendOrders, Long totalReceiveOrders) {
        super(pointId, name, totalSendOrders, totalReceiveOrders);
    }
}


