package com.magicpost.app.magicPost.address;

import com.magicpost.app.magicPost.address.dto.AddressDTO;
import com.magicpost.app.magicPost.address.entity.Address;
import com.magicpost.app.magicPost.address.entity.Commune;
import com.magicpost.app.magicPost.address.entity.District;
import com.magicpost.app.magicPost.address.repository.CommuneRepository;
import com.magicpost.app.magicPost.address.repository.DistrictRepository;
import com.magicpost.app.magicPost.address.entity.Province;
import com.magicpost.app.magicPost.address.repository.ProvinceRepository;
import com.magicpost.app.magicPost.exception.InvalidRequestDataException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final CommuneRepository communeRepository;

    public Address checkAndCreateAddress(AddressDTO addressRequest) {
        if (addressRequest == null) return null;
        if (addressRequest.getCommune() == null ||
                addressRequest.getDistrict() == null ||
                addressRequest.getProvince() == null)
            throw new InvalidRequestDataException("The address must include all of commune, district, province");

        Province province = provinceRepository.findByName(addressRequest.getProvince()).
                orElseGet(() -> provinceRepository.save(new Province(addressRequest.getProvince())));

        Optional<District> optionalDistrict = province.getDistricts().stream()
                .filter(e -> e.getName().equals(addressRequest.getDistrict()))
                .findFirst();
        District district;
        if (optionalDistrict.isEmpty()) {
            district = districtRepository.save(new District(addressRequest.getDistrict(), province));
            province.getDistricts().add(district);
            provinceRepository.save(province);
        } else district = optionalDistrict.get();

        Optional<Commune> optionalCommune = district.getCommunes().stream()
                .filter(c -> c.getName().equals(addressRequest.getCommune()))
                .findFirst();
        Commune commune;
        if (optionalCommune.isEmpty()) {
            commune = communeRepository.save(new Commune(addressRequest.getCommune(), district));
            district.getCommunes().add(commune);
            districtRepository.save(district);
//            provinceRepository.save(province);
        } else commune = optionalCommune.get();
        return new Address(addressRequest.getStreet(), addressRequest.getZipcode(), commune);
    }
}
