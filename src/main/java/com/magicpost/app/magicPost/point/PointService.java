package com.magicpost.app.magicPost.point;

import com.magicpost.app.magicPost.address.AddressService;
import com.magicpost.app.magicPost.address.entity.Address;
import com.magicpost.app.magicPost.exception.ResourceAlreadyExistsException;
import com.magicpost.app.magicPost.exception.ResourceNotFoundException;
import com.magicpost.app.magicPost.order.dto.ExpressOrderResponse;
import com.magicpost.app.magicPost.point.dto.*;
import com.magicpost.app.magicPost.point.entity.GatheringPoint;
import com.magicpost.app.magicPost.point.entity.Point;
import com.magicpost.app.magicPost.point.entity.TransactionPoint;
import com.magicpost.app.magicPost.point.repository.GatheringPointRepository;
import com.magicpost.app.magicPost.point.repository.PointRepository;
import com.magicpost.app.magicPost.point.repository.TransactionPointRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PointService {
    private final AddressService addressService;
    private final ModelMapper modelMapper;
    private final PointRepository<Point> pointRepository;
    private final GatheringPointRepository gatheringPointRepository;
    private final TransactionPointRepository transactionPointRepository;


    public List<PointResponse> getAllPoints() {
        return pointRepository.findAll().stream()
                .map(element ->
                        (element instanceof TransactionPoint) ?
                                modelMapper.map(element, TransactionPointResponse.class) :
                                modelMapper.map(element, GatheringPointResponse.class))
                .toList();

    }

    public List<GatheringPointResponse> getAllGatheringPoints() {
        List<GatheringPoint> gatheringPoints = gatheringPointRepository.findAll();
        return gatheringPoints.stream()
                .map((element) -> modelMapper.map(element, GatheringPointResponse.class))
                .toList();
    }

    public List<TransactionPointResponse> getAllTransactionPoints() {
        List<TransactionPoint> transactionPoints = transactionPointRepository.findAll();
        return transactionPoints.stream()
                .map((element) -> modelMapper.map(element, TransactionPointResponse.class))
                .toList();
    }

    @Transactional
    public GatheringPointResponse createNewGatheringPoint(PointRequest pointRequest) {
        if (gatheringPointRepository.existsByName(pointRequest.getName()))
            throw new ResourceAlreadyExistsException("Gathering Point");
        Address address = addressService.checkAndCreateAddress(pointRequest.getAddress());
        GatheringPoint newGatheringPoint = modelMapper.map(pointRequest, GatheringPoint.class);
        newGatheringPoint.setAddress(address);
        newGatheringPoint = pointRepository.save(newGatheringPoint);

        return modelMapper.map(newGatheringPoint, GatheringPointResponse.class);
    }

    @Transactional
    public TransactionPointResponse createNewTransactionPoint(Long gatheringPointId, PointRequest pointRequest) {
        GatheringPoint gatheringPoint = gatheringPointRepository.findById(gatheringPointId)
                .orElseThrow(() -> new ResourceNotFoundException("Gathering Point"));
        if (transactionPointRepository.existsByName(pointRequest.getName())) {
            throw new ResourceAlreadyExistsException("Transaction Point");
        }

        Address address = addressService.checkAndCreateAddress(pointRequest.getAddress());
        TransactionPoint newTransactionPoint = modelMapper.map(pointRequest, TransactionPoint.class);
        newTransactionPoint.setAddress(address);
        newTransactionPoint.setGatheringPoint(gatheringPoint);

        newTransactionPoint = transactionPointRepository.save(newTransactionPoint);
        gatheringPoint.getManageTransactionPoints().add(newTransactionPoint);
        gatheringPointRepository.save(gatheringPoint);

        TransactionPointResponse transactionPointResponse = modelMapper.map(newTransactionPoint, TransactionPointResponse.class);
        transactionPointResponse.setGatheringPointId(newTransactionPoint.getGatheringPoint().getId());
        return transactionPointResponse;
    }

    @Transactional
    public PointResponse updatePoint(Long pointId, PointRequest pointRequest) {
        Point point = pointRepository.findById(pointId)
                .orElseThrow(() -> new ResourceNotFoundException("Point"));
        if (pointRequest.getName() != null)
            point.setName(pointRequest.getName());
        if (pointRequest.getPhone() != null)
            point.setPhone(pointRequest.getPhone());
        if (pointRequest.getEmail() != null)
            point.setEmail(pointRequest.getEmail());
        if (pointRequest.getAddress() != null) {
            Address newAddress = addressService.checkAndCreateAddress(pointRequest.getAddress());
            point.setAddress(newAddress);
        }
        return modelMapper.map(pointRepository.save(point), PointResponse.class);
    }

    @Transactional
    public TransactionPointResponse changeGatheringPointOfTransactionPoint(Long transactionPointId, TransactionPointRequest transactionPointRequest) {
        Long gatheringPointId = transactionPointRequest.getGatheringPointId();
        GatheringPoint gatheringPoint = gatheringPointRepository.findById(gatheringPointId)
                .orElseThrow(() -> new ResourceNotFoundException("Gathering Point"));
        TransactionPoint transactionPoint = transactionPointRepository.findById(transactionPointId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction Point"));
        transactionPoint.setGatheringPoint(gatheringPoint);
        gatheringPoint.getManageTransactionPoints().add(transactionPoint);
        gatheringPointRepository.save(gatheringPoint);
        return modelMapper.map(transactionPointRepository.save(transactionPoint), TransactionPointResponse.class);
    }

    @Transactional
    public void deletePoint(Long pointId) {
        pointRepository.deleteById(pointId);
    }

    public List<ExpressOrderResponse> getInventoryOfPoint(Long pointId) {
        Point point = pointRepository.findById(pointId)
                .orElseThrow(() -> new ResourceNotFoundException("Point"));
        return point.getInventory().values().stream()
                .map((element) -> modelMapper.map(element, ExpressOrderResponse.class))
                .toList();
    }
}
