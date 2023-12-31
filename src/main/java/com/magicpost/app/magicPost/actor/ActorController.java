package com.magicpost.app.magicPost.actor;

import com.magicpost.app.magicPost.actor.entity.Shipper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ActorController {
    private final ActorService actorService;

    @PreAuthorize("hasRole('TRANSACTIONSTAFF') and @customAuthorization.belongsPoint(authentication,#transactionPointId)")
    @GetMapping("/transaction-points/{transaction-point-id}/shippers")
    ResponseEntity<?> getAllShipperInTransPoint(@PathVariable("transaction-point-id") Long transactionPointId) {
        Set<Shipper> shippers = actorService.getAllShipperInTransPoint(transactionPointId);
        return shippers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(shippers);
    }

    @PreAuthorize("hasRole('TRANSACTIONSTAFF') and @customAuthorization.belongsPoint(authentication,#transactionPointId)")
    @PostMapping("/transaction-points/{transaction-point-id}/shippers")
    ResponseEntity<?> addNewShipperToTransPoint(@PathVariable("transaction-point-id") Long transactionPointId,
                                                @RequestBody @Valid Shipper shipper) {
        Shipper newShipper = actorService.addNewShipperToTransPoint(transactionPointId, shipper);
        return ResponseEntity.created(URI.create("transaction-points/" + transactionPointId + "/shippers/" + newShipper.getId()))
                .body(newShipper);
    }

    @PreAuthorize("hasRole('TRANSACTIONSTAFF') and @customAuthorization.belongsPoint(authentication,#transactionPointId)")
    @DeleteMapping("/transaction-points/{transaction-point-id}/shippers/{shipper-id}")
    ResponseEntity<?> removeShipperAtTransPoint(@PathVariable("transaction-point-id") Long transactionPointId,
                                                @PathVariable("shipper-id")UUID shipperId){
        actorService.removeShipperAtTransPoint(transactionPointId, shipperId);
        return ResponseEntity.noContent().build();
    }
}
