package com.magicpost.app.magicPost.point;

import com.magicpost.app.magicPost.point.dto.statistic.GatheringStatisticalResponse;
import com.magicpost.app.magicPost.point.dto.statistic.StatisticalResponse;
import com.magicpost.app.magicPost.point.dto.statistic.TransactionStatisticalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StatisticController {
    private final PointService pointService;

    @GetMapping("gathering-points/{gathering-point-id}/statistic")
    ResponseEntity<?> getStatisticOfGathering(@PathVariable("gathering-point-id") Long gatheringPointId){
        GatheringStatisticalResponse statisticalResponse = pointService.getStatisticOfGathering(gatheringPointId);
        return ResponseEntity.ok(statisticalResponse);
    }

    @GetMapping("transaction-points/{transaction-point-id}/statistic")
    ResponseEntity<?> getStatisticOfTransaction(@PathVariable("transaction-point-id") Long transactionPointId){
        TransactionStatisticalResponse statisticalResponse = pointService.getStatisticOfTransaction(transactionPointId);
        return ResponseEntity.ok(statisticalResponse);
    }

    @GetMapping("/points/statistic")
    ResponseEntity<?> getAllStatistic(){
        List<StatisticalResponse> responses = pointService.getAllStatistic();
        return ResponseEntity.ok(responses);
    }
}
