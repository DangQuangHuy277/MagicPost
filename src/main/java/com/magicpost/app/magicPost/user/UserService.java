package com.magicpost.app.magicPost.user;

import com.magicpost.app.magicPost.exception.ResourceAlreadyExistsException;
import com.magicpost.app.magicPost.exception.ResourceNotFoundException;
import com.magicpost.app.magicPost.point.entity.GatheringPoint;
import com.magicpost.app.magicPost.point.entity.Point;
import com.magicpost.app.magicPost.point.entity.TransactionPoint;
import com.magicpost.app.magicPost.point.repository.GatheringPointRepository;
import com.magicpost.app.magicPost.point.repository.PointRepository;
import com.magicpost.app.magicPost.point.repository.TransactionPointRepository;
import com.magicpost.app.magicPost.user.dto.UserResponse;
import com.magicpost.app.magicPost.user.entity.User;
import com.magicpost.app.magicPost.user.entity.leader.CompanyLeader;
import com.magicpost.app.magicPost.user.entity.leader.GatheringLeader;
import com.magicpost.app.magicPost.user.entity.leader.Leader;
import com.magicpost.app.magicPost.user.entity.leader.TransactionLeader;
import com.magicpost.app.magicPost.user.entity.staff.GatheringStaff;
import com.magicpost.app.magicPost.user.entity.staff.TransactionStaff;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GatheringPointRepository gatheringPointRepository;
    private final TransactionPointRepository transactionPointRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final PointRepository<Point> pointRepository;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(user -> {
            UserResponse userResponse = modelMapper.map(user, UserResponse.class);
            userResponse.setRole(user.getClass().getSimpleName());
            return userResponse;
        }).toList();

    }

    public UserResponse createNewGatheringLeader(Long gatheringPointId, GatheringLeader gatheringLeader) {
        GatheringPoint gatheringPoint = gatheringPointRepository.findById(gatheringPointId)
                .orElseThrow(() -> new ResourceNotFoundException("Gathering Point"));
        if (gatheringPoint.getGatheringLeader() != null)
            throw new ResourceAlreadyExistsException("Gathering Leader of this point");
        gatheringLeader.setPassword(passwordEncoder.encode(gatheringLeader.getPassword()));
        gatheringLeader.setGatheringPoint(gatheringPoint);
        gatheringLeader = userRepository.save(gatheringLeader);

        gatheringPoint.setGatheringLeader(gatheringLeader);
        gatheringPointRepository.save(gatheringPoint);
        return modelMapper.map(gatheringLeader, UserResponse.class);
    }

    public UserResponse createNewTransactionLeader(Long transactionPointId, TransactionLeader transactionLeader) {
        TransactionPoint transactionPoint = transactionPointRepository.findById(transactionPointId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction Point"));
        if (transactionPoint.getTransactionLeader() != null)
            throw new ResourceAlreadyExistsException("Transaction Leader of this point");
        transactionLeader.setPassword(passwordEncoder.encode(transactionLeader.getPassword()));
        transactionLeader.setTransactionPoint(transactionPoint);
        transactionLeader = userRepository.save(transactionLeader);

        transactionPoint.setTransactionLeader(transactionLeader);
        transactionPointRepository.save(transactionPoint);
        return modelMapper.map(userRepository.save(transactionLeader), UserResponse.class);
    }

    public UserResponse createNewGatheringStaff(Long gatheringPointId, GatheringStaff gatheringStaff) {
        GatheringPoint gatheringPoint = gatheringPointRepository.findById(gatheringPointId)
                .orElseThrow(() -> new ResourceNotFoundException("Gathering Point"));
        gatheringStaff.setPassword(passwordEncoder.encode(gatheringStaff.getPassword()));
        gatheringStaff.setGatheringPoint(gatheringPoint);
        gatheringStaff = userRepository.save(gatheringStaff);

        gatheringPoint.getGatheringStaffs().add(gatheringStaff);
        gatheringPointRepository.save(gatheringPoint);
        return modelMapper.map(gatheringStaff, UserResponse.class);
    }

    public UserResponse createNewTransactionStaff(Long transactionPointId, TransactionStaff transactionStaff) {
        TransactionPoint transactionPoint = transactionPointRepository.findById(transactionPointId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction Point"));
        transactionStaff.setPassword(passwordEncoder.encode(transactionStaff.getPassword()));
        transactionStaff.setTransactionPoint(transactionPoint);
        transactionStaff = userRepository.save(transactionStaff);

        transactionPoint.getTransactionStaffs().add(transactionStaff);
        transactionPointRepository.save(transactionPoint);
        return modelMapper.map(transactionStaff, UserResponse.class);
    }

    public UserResponse createNewCompanyLeader(CompanyLeader companyLeader) {
        companyLeader.setPassword(passwordEncoder.encode(companyLeader.getPassword()));
        userRepository.save(companyLeader);
        return modelMapper.map(companyLeader, UserResponse.class);
    }


    public void removeStaffAtTransaction(Long transactionPointId, Long transactionStaffId) {
        TransactionPoint transactionPoint = transactionPointRepository.findById(transactionPointId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction Point"));
        TransactionStaff transactionStaff = transactionPoint.getTransactionStaffs().stream()
                .filter(transactionStaff1 -> transactionStaff1.getId().equals(transactionStaffId))
                .findAny().orElseThrow(() -> new ResourceNotFoundException("Transaction Staff"));
        transactionPoint.getTransactionStaffs().remove(transactionStaff);
        transactionPointRepository.save(transactionPoint);
        userRepository.delete(transactionStaff);
    }

    public void removeStaffAtGathering(Long gatheringPointId, Long gatheringStaffId) {
        GatheringPoint gatheringPoint = gatheringPointRepository.findById(gatheringPointId)
                .orElseThrow(() -> new ResourceNotFoundException("Gathering Point"));
        GatheringStaff gatheringStaff = gatheringPoint.getGatheringStaffs().stream()
                .filter(gatheringStaff1 -> gatheringStaff1.getId().equals(gatheringStaffId))
                .findAny().orElseThrow(() -> new ResourceNotFoundException("Gathering Staff"));
        gatheringPoint.getGatheringStaffs().remove(gatheringStaff);
        gatheringPointRepository.save(gatheringPoint);
        userRepository.delete(gatheringStaff);
    }

    public List<UserResponse> getAllLeaders() {
        return userRepository.findAll().stream()
                .filter(user -> user instanceof Leader)
                .map((element) -> modelMapper.map(element, UserResponse.class))
                .toList();
    }

    public void removeLeaderAtPoint(Long pointId) {
        Point point = pointRepository.findById(pointId)
                .orElseThrow(() -> new ResourceNotFoundException("Point"));
        switch (point){
            case GatheringPoint gp -> {
                GatheringLeader gl = gp.getGatheringLeader();
                gp.setGatheringLeader(null);
                userRepository.delete(gl);
            }
            case TransactionPoint tp -> {
                TransactionLeader tl = tp.getTransactionLeader();
                tp.setTransactionLeader(null);
                userRepository.delete(tl);
            }
            default -> throw new IllegalStateException("Unexpected value: " + point);
        }
    }
}
