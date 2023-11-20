package com.magicpost.app.magicPost.point;

import com.magicpost.app.magicPost.point.dto.GatheringPointResponse;
import com.magicpost.app.magicPost.point.dto.PointRequest;
import com.magicpost.app.magicPost.point.dto.PointResponse;
import com.magicpost.app.magicPost.point.dto.TransactionPointResponse;
import com.magicpost.app.magicPost.point.entity.GatheringPoint;
import com.magicpost.app.magicPost.point.entity.Point;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PointController {
    private final PointService pointService;

    @GetMapping("/points")
    ResponseEntity<?> getAllPoints() {
        List<PointResponse> points = pointService.getAllPoints();

        return points.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(points);
    }

    @GetMapping("/gathering-points")
    ResponseEntity<?> getAllGatheringPoints() {
        List<GatheringPointResponse> gatheringPoints = pointService.getAllGatheringPoints();
        return gatheringPoints.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(gatheringPoints);
    }

    @GetMapping("/transaction-points")
    ResponseEntity<?> getAllTransactionPoints() {
        List<TransactionPointResponse> transactionPoints = pointService.getAllTransactionPoints();
        return transactionPoints.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(transactionPoints);
    }

    @PostMapping("/gathering-points")
    ResponseEntity<?> createNewGatheringPoint(@Valid @RequestBody PointRequest pointRequest) {
        GatheringPointResponse newPoint = pointService.createNewGatheringPoint(pointRequest);
        return ResponseEntity.created(URI.create("/api/v1/points/" + newPoint.getId()))
                .body(newPoint);
    }

    @PostMapping("/gathering-points/{gathering-point-id}/transaction-points")
    ResponseEntity<?> createNewTransactionPoint(@PathVariable("gathering-point-id") Long gatheringPointId,
                                                @Valid @RequestBody PointRequest pointRequest) {
        TransactionPointResponse newPoint = pointService.createNewTransactionPoint(gatheringPointId, pointRequest);
        return ResponseEntity.created(URI.create("/api/v1/points/" + newPoint.getId()))
                .body(newPoint);
    }
}
