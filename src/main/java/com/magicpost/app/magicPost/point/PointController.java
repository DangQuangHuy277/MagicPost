package com.magicpost.app.magicPost.point;

import com.magicpost.app.magicPost.order.dto.ExpressOrderResponse;
import com.magicpost.app.magicPost.point.dto.*;
import com.magicpost.app.magicPost.point.entity.GatheringPoint;
import com.magicpost.app.magicPost.point.entity.Point;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/gathering-points/{gathering-point-id}")
    ResponseEntity<?> getDetailsOfGatherPoint(@PathVariable("gathering-point-id") Long gatheringPointId){
        GatheringPointResponse gatheringPointResponse = pointService.getDetailsOfGatherPoint(gatheringPointId);
        return ResponseEntity.ok(gatheringPointResponse);
    }

    @GetMapping("/gathering-points/{gathering-point-id}/transaction-points")
    ResponseEntity<?> getManagementTransactionPointOfGathering(@PathVariable("gathering-point-id") Long gatheringPointId){
        List<TransactionPointResponse> pointResponses = pointService.getManagementTransactionPointOfGathering(gatheringPointId);
        return pointResponses.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(pointResponses);
    }

    @GetMapping("/transaction-points")
    ResponseEntity<?> getAllTransactionPoints() {
        List<TransactionPointResponse> transactionPoints = pointService.getAllTransactionPoints();
        return transactionPoints.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(transactionPoints);
    }

    @GetMapping("/transaction-points/{transaction-point-id}")
    ResponseEntity<?> getDetailOfTransPoint(@PathVariable("transaction-point-id") Long transactionPointId){
        TransactionPointResponse transactionPointResponse = pointService.getDetailOfTransPoint(transactionPointId);
        return ResponseEntity.ok(transactionPointResponse);
    }

    @PreAuthorize("hasRole('COMPANYLEADER')")
    @PostMapping("/gathering-points")
    ResponseEntity<?> createNewGatheringPoint(@Valid @RequestBody PointRequest pointRequest) {
        GatheringPointResponse newPoint = pointService.createNewGatheringPoint(pointRequest);
        return ResponseEntity.created(URI.create("/api/v1/points/" + newPoint.getId()))
                .body(newPoint);
    }


    @PreAuthorize("hasRole('COMPANYLEADER')")
    @PostMapping("/gathering-points/{gathering-point-id}/transaction-points")
    ResponseEntity<?> createNewTransactionPoint(@PathVariable("gathering-point-id") Long gatheringPointId,
                                                @Valid @RequestBody PointRequest pointRequest) {
        TransactionPointResponse newPoint = pointService.createNewTransactionPoint(gatheringPointId, pointRequest);
        return ResponseEntity.created(URI.create("/api/v1/points/" + newPoint.getId()))
                .body(newPoint);
    }

    @PreAuthorize("hasRole('COMPANYLEADER')")
    @PatchMapping("/points/{point-id}")
    ResponseEntity<?> updatePoint(@PathVariable("point-id") Long pointId,
                                  @RequestBody PointRequest pointRequest) {
        PointResponse updatedPoint = pointService.updatePoint(pointId, pointRequest);
        return ResponseEntity.ok(updatedPoint);
    }

    @PreAuthorize("hasRole('COMPANYLEADER')")
    @PatchMapping("transaction-points/{transaction-point-id}")
    ResponseEntity<?> changeGatheringPointOfTransactionPoint(@PathVariable("transaction-point-id") Long transactionPointId,
                                                             @RequestBody TransactionPointRequest transactionPointRequest) {
        TransactionPointResponse updatedTransactionPoint =
                pointService.changeGatheringPointOfTransactionPoint(transactionPointId, transactionPointRequest);
        return ResponseEntity.ok(updatedTransactionPoint);
    }
    @PreAuthorize("hasRole('COMPANYLEADER')")
    @DeleteMapping("/points/{point-id}")
    ResponseEntity<?> deletePoint(@PathVariable("point-id") Long pointId){
        pointService.deletePoint(pointId);
        return ResponseEntity.noContent().build();
    }


    @PreAuthorize("(hasAnyRole('TRANSACTIONSTAFF','GATHERINGSTAFF') and @customAuthorization.belongsPoint(authentication,#pointId)) or hasRole('COMPANYLEADER')")
    @GetMapping("points/{point-id}/inventory")
    ResponseEntity<?> getInventoryOfPoint(@PathVariable("point-id") Long pointId){
        List<ExpressOrderResponse> inventory = pointService.getInventoryOfPoint(pointId);
        return inventory.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(inventory);
    }


}
