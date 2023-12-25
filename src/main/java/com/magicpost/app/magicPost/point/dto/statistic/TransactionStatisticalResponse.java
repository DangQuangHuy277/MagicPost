package com.magicpost.app.magicPost.point.dto.statistic;

import lombok.*;


@Getter
@Setter
public class TransactionStatisticalResponse extends StatisticalResponse {
    private Long successOrders;
    private Long cancelOrders;

    public TransactionStatisticalResponse(Long pointId, String name, Long totalSendOrders, Long totalReceiveOrders) {
        super(pointId, name, totalSendOrders, totalReceiveOrders);
    }
}
