package com.magicpost.app.magicPost.point.dto.statistic;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
public class TransactionStatisticalResponse extends StatisticalResponse {
    private Long successOrders;
    private Long cancelOrders;
}
