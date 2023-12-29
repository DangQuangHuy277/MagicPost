package com.magicpost.app.magicPost.user;

import com.magicpost.app.magicPost.user.dto.UserResponse;
import com.magicpost.app.magicPost.user.entity.User;
import com.magicpost.app.magicPost.user.entity.leader.CompanyLeader;
import com.magicpost.app.magicPost.user.entity.leader.GatheringLeader;
import com.magicpost.app.magicPost.user.entity.leader.TransactionLeader;
import com.magicpost.app.magicPost.user.entity.staff.GatheringStaff;
import com.magicpost.app.magicPost.user.entity.staff.TransactionStaff;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/company-leader")
    ResponseEntity<?> createNewUser(@Valid @RequestBody CompanyLeader user) {
        UserResponse returnUser = userService.createNewCompanyLeader(user);
        return ResponseEntity.created(URI.create("/api/v1/users/" + returnUser.getId())).body(returnUser);
    }


    @PreAuthorize("hasRole(COMPANYLEADER')")
    @PostMapping("/gathering-points/{gathering-point-id}/gathering-leader")
    ResponseEntity<?> createNewGatheringLeader(@PathVariable("gathering-point-id") Long gatheringPointId,
                                               @Valid @RequestBody GatheringLeader gatheringLeader) {
        UserResponse returnUser = userService.createNewGatheringLeader(gatheringPointId, gatheringLeader);
        return ResponseEntity.created(URI.create("/api/v1/gatheringLeader/" + returnUser.getId()))
                .body(returnUser);
    }

    @PreAuthorize("hasRole(COMPANYLEADER')")
    @PostMapping("/transaction-points/{transaction-point-id}/transaction-leader")
    ResponseEntity<?> createNewTransactionLeader(@PathVariable("transaction-point-id") Long transactionPointId,
                                                 @Valid @RequestBody TransactionLeader transactionLeader) {
        UserResponse returnUser = userService.createNewTransactionLeader(transactionPointId, transactionLeader);
        return ResponseEntity.created(URI.create("/api/v1/transactionLeader/" + returnUser.getId()))
                .body(returnUser);
    }

    @PreAuthorize("hasRole('GATHERINGLEADER') and @customAuthorization.belongsPoint(authentication,#gatheringPointId)")
    @PostMapping("/gathering-points/{gathering-point-id}/gathering-staffs")
    ResponseEntity<?> createNewGatheringStaff(@PathVariable("gathering-point-id") Long gatheringPointId,
                                              @Valid @RequestBody GatheringStaff gatheringStaff) {
        UserResponse returnUser = userService.createNewGatheringStaff(gatheringPointId, gatheringStaff);
        return ResponseEntity.created(URI.create("/api/v1/transactionLeader/" + returnUser.getId()))
                .body(returnUser);
    }

    @PreAuthorize("hasRole('TRANSACTIONLEADER') and @customAuthorization.belongsPoint(authentication,#transactionPointId)")
    @PostMapping("/transaction-points/{transaction-point-id}/transaction-staffs")
    ResponseEntity<?> createNewTransactionStaff(@PathVariable("transaction-point-id") Long transactionPointId,
                                                @Valid @RequestBody TransactionStaff transactionStaff) {
        UserResponse returnUser = userService.createNewTransactionStaff(transactionPointId, transactionStaff);
        return ResponseEntity.created(URI.create("/api/v1/transactionLeader/" + returnUser.getId()))
                .body(returnUser);
    }

    @PreAuthorize("hasRole('COMPANYLEADER')")
    @DeleteMapping("/users/{user-id}")
    ResponseEntity<?> deleteUser(@PathVariable("user-id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('TRANSACTIONLEADER') and @customAuthorization.belongsPoint(authentication,#transactionPointId)")
    @PostMapping("/transaction-points/{transaction-point-id}/transaction-staffs/{transaction-staff-id}")
    ResponseEntity<?> removeStaffAtTransaction(@PathVariable("transaction-point-id") Long transactionPointId,
                                               @PathVariable("transaction-staff-id")Long transactionStaffId){
        userService.removeStaffAtTransaction(transactionPointId, transactionStaffId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('GATHERINGLEADER') and @customAuthorization.belongsPoint(authentication,#gatheringPointId)")
    @PostMapping("/gathering-points/{gathering-point-id}/gathering-staffs/{transaction-staff-id}")
    ResponseEntity<?> removeStaffAtGathering(@PathVariable("transaction-point-id") Long gatheringPointId,
                                               @PathVariable("transaction-staff-id")Long gatheringStaffId){
        userService.removeStaffAtGathering(gatheringPointId, gatheringStaffId);
        return ResponseEntity.noContent().build();
    }
}
