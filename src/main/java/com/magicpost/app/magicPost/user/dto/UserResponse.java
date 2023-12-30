package com.magicpost.app.magicPost.user.dto;

import com.magicpost.app.magicPost.user.entity.User;
import com.magicpost.app.magicPost.user.entity.leader.GatheringLeader;
import com.magicpost.app.magicPost.user.entity.leader.TransactionLeader;
import com.magicpost.app.magicPost.user.entity.staff.GatheringStaff;
import com.magicpost.app.magicPost.user.entity.staff.TransactionStaff;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String role;
    private Long pointId;
}
