package com.magicpost.app.magicPost.point.dto.statistic;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatisticalResponse {
    private Long id;
    private String name;
    private Long totalSendOrders;
    private Long totalReceiveOrders;
    private String type;

    public StatisticalResponse(Long pointId, String name, Long totalSendOrders, Long totalReceiveOrders) {
        this.id = pointId;
        this.name = name;
        this.totalSendOrders = totalSendOrders;
        this.totalReceiveOrders = totalReceiveOrders;
    }
}
