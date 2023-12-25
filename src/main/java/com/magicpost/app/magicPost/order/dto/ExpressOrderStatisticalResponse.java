package com.magicpost.app.magicPost.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExpressOrderStatisticalResponse {
    private Long totalOrders;
    private Long totalSuccessOrders;
    private Long totalCancelOrders;
}
