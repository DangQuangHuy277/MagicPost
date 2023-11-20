package com.magicpost.app.magicPost.point;

import com.magicpost.app.magicPost.address.AddressService;
import com.magicpost.app.magicPost.address.entity.Address;
import com.magicpost.app.magicPost.exception.ResourceAlreadyExistsException;
import com.magicpost.app.magicPost.exception.ResourceNotFoundException;
import com.magicpost.app.magicPost.point.dto.GatheringPointResponse;
import com.magicpost.app.magicPost.point.dto.PointRequest;
import com.magicpost.app.magicPost.point.dto.PointResponse;
import com.magicpost.app.magicPost.point.dto.TransactionPointResponse;
import com.magicpost.app.magicPost.point.entity.GatheringPoint;
import com.magicpost.app.magicPost.point.entity.Point;
import com.magicpost.app.magicPost.point.entity.TransactionPoint;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PointService {
    private final AddressService addressService;
    private final ModelMapper modelMapper;
    private final PointRepository<Point> pointRepository;
    private final PointRepository<GatheringPoint> gatheringPointRepository;
    private final PointRepository<TransactionPoint> transactionPointRepository;


    public List<PointResponse> getAllPoints() {
        return pointRepository.findAll().stream()
                .map(element ->
                        (element instanceof TransactionPoint) ?
                                modelMapper.map(element, TransactionPointResponse.class) :
                                modelMapper.map(element, GatheringPointResponse.class))
                .toList();

    }

    public List<GatheringPointResponse> getAllGatheringPoints() {
//        List<GatheringPoint> gatheringPoints = pointRepository.findAllGatheringPoint();
        List<GatheringPoint> gatheringPoints = gatheringPointRepository.findAll();
        return gatheringPoints.stream()
                .map((element) -> modelMapper.map(element, GatheringPointResponse.class))
                .toList();
    }

    public List<TransactionPointResponse> getAllTransactionPoints() {
//        List<TransactionPoint> transactionPoints = pointRepository.findAllTransactionPoint();
        List<TransactionPoint> transactionPoints = transactionPointRepository.findAll();
        return transactionPoints.stream()
                .map((element) -> modelMapper.map(element, TransactionPointResponse.class))
                .toList();
    }

    public GatheringPointResponse createNewGatheringPoint(PointRequest pointRequest) {
        if (gatheringPointRepository.existsByName(pointRequest.getName()))
            throw new ResourceAlreadyExistsException("Gathering Point");
        Address address = addressService.checkAndCreateAddress(pointRequest.getAddress());
        GatheringPoint newGatheringPoint = modelMapper.map(pointRequest, GatheringPoint.class);
        newGatheringPoint.setAddress(address);
        newGatheringPoint = pointRepository.save(newGatheringPoint);

        return modelMapper.map(newGatheringPoint, GatheringPointResponse.class);
    }

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
}
