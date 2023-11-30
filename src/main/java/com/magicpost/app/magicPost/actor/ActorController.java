package com.magicpost.app.magicPost.actor;

import com.magicpost.app.magicPost.actor.entity.Shipper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ActorController {
    private final ActorService actorService;

    @GetMapping("transaction-points/{transaction-point-id}/shippers")
    ResponseEntity<?> getAllShipperInTransPoint(@PathVariable("transaction-point-id") Long transactionPointId) {
        Set<Shipper> shippers = actorService.getAllShipperInTransPoint(transactionPointId);
        return shippers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(shippers);
    }

    @PostMapping("transaction-points/{transaction-point-id}/shippers")
    ResponseEntity<?> addNewShipperToTransPoint(@PathVariable("transaction-point-id") Long transactionPointId,
                                                @RequestBody @Valid Shipper shipper) {
        Shipper newShipper = actorService.addNewShipperToTransPoint(transactionPointId, shipper);
        return ResponseEntity.created(URI.create("transaction-points/" + transactionPointId + "/shippers/" + newShipper.getId()))
                .body(newShipper);
    }

    @DeleteMapping("transaction-points/{transaction-point-id}/shippers/{shipper-id}")
    ResponseEntity<?> removeShipperAtTransPoint(@PathVariable("transaction-point-id") Long transactionPointId,
                                                @PathVariable("shipper-id")UUID shipperId){
        actorService.removeShipperAtTransPoint(transactionPointId, shipperId);
        return ResponseEntity.noContent().build();
    }
}
