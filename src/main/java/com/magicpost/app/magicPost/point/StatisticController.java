package com.magicpost.app.magicPost.point;

import com.magicpost.app.magicPost.point.dto.statistic.GatheringStatisticalResponse;
import com.magicpost.app.magicPost.point.dto.statistic.StatisticalResponse;
import com.magicpost.app.magicPost.point.dto.statistic.TransactionStatisticalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StatisticController {
    private final PointService pointService;

    @PreAuthorize("(hasRole('GATHERINGLEADER') and @customAuthorization.belongsPoint(authentication,#gatheringPointId)) or hasRole('COMPANYLEADER")
    @GetMapping("gathering-points/{gathering-point-id}/statistic")
    ResponseEntity<?> getStatisticOfGathering(@PathVariable("gathering-point-id") Long gatheringPointId){
        GatheringStatisticalResponse statisticalResponse = pointService.getStatisticOfGathering(gatheringPointId);
        return ResponseEntity.ok(statisticalResponse);
    }

    @PreAuthorize("(hasRole('TRANSACTIONLEADER') and @customAuthorization.belongsPoint(authentication,#transactionPointId)) or hasRoler('COMPANYLEADER')")
    @GetMapping("transaction-points/{transaction-point-id}/statistic")
    ResponseEntity<?> getStatisticOfTransaction(@PathVariable("transaction-point-id") Long transactionPointId){
        TransactionStatisticalResponse statisticalResponse = pointService.getStatisticOfTransaction(transactionPointId);
        return ResponseEntity.ok(statisticalResponse);
    }

    @PreAuthorize("hasRole('COMPANYLEADER')")
    @GetMapping("/points/statistic")
    ResponseEntity<?> getAllStatistic(){
        List<StatisticalResponse> responses = pointService.getAllStatistic();
        return ResponseEntity.ok(responses);
    }
}
