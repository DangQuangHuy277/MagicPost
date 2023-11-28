package com.magicpost.app.magicPost.user;

import com.magicpost.app.magicPost.user.dto.UserResponse;
import com.magicpost.app.magicPost.user.entity.User;
import com.magicpost.app.magicPost.user.entity.leader.GatheringLeader;
import com.magicpost.app.magicPost.user.entity.leader.TransactionLeader;
import com.magicpost.app.magicPost.user.entity.staff.GatheringStaff;
import com.magicpost.app.magicPost.user.entity.staff.TransactionStaff;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/users")
    ResponseEntity<?> createNewUser(@Valid @RequestBody User user, @RequestParam String type) {
        User returnUser = userService.createNewUser(user, type);
        return ResponseEntity.created(URI.create("/api/v1/users/" + returnUser.getId())).body(returnUser);
    }

    @PostMapping("/gathering-points/{gathering-point-id}/gathering-leader")
    ResponseEntity<?> createNewGatheringLeader(@PathVariable("gathering-point-id") Long gatheringPointId,
                                               @Valid @RequestBody GatheringLeader gatheringLeader) {
        UserResponse returnUser = userService.createNewGatheringLeader(gatheringPointId, gatheringLeader);
        return ResponseEntity.created(URI.create("/api/v1/gatheringLeader/" + returnUser.getId()))
                .body(returnUser);
    }

    @PostMapping("/transaction-points/{transaction-point-id}/transaction-leader")
    ResponseEntity<?> createNewTransactionLeader(@PathVariable("transaction-point-id") Long transactionPointId,
                                                 @Valid @RequestBody TransactionLeader transactionLeader) {
        UserResponse returnUser = userService.createNewTransactionLeader(transactionPointId, transactionLeader);
        return ResponseEntity.created(URI.create("/api/v1/transactionLeader/" + returnUser.getId()))
                .body(returnUser);
    }

    @PostMapping("/gathering-points/{gathering-point-id}/gathering-staffs")
    ResponseEntity<?> createNewGatheringStaff(@PathVariable("gathering-point-id") Long gatheringPointId,
                                              @Valid @RequestBody GatheringStaff gatheringStaff) {
        UserResponse returnUser = userService.createNewGatheringStaff(gatheringPointId, gatheringStaff);
        return ResponseEntity.created(URI.create("/api/v1/transactionLeader/" + returnUser.getId()))
                .body(returnUser);
    }

    @PostMapping("/transaction-points/{transaction-point-id}/transaction-staffs")
    ResponseEntity<?> createNewTransactionStaff(@PathVariable("transaction-point-id") Long transactionPointId,
                                                @Valid @RequestBody TransactionStaff transactionStaff) {
        UserResponse returnUser = userService.createNewTransactionStaff(transactionPointId, transactionStaff);
        return ResponseEntity.created(URI.create("/api/v1/transactionLeader/" + returnUser.getId()))
                .body(returnUser);
    }

    @DeleteMapping("/users/{user-id}")
    ResponseEntity<?> deleteUser(@PathVariable("user-id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}