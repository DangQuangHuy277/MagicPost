package com.magicpost.app.magicPost.config;

import com.magicpost.app.magicPost.address.dto.AddressDTO;
import com.magicpost.app.magicPost.address.entity.Address;
import com.magicpost.app.magicPost.point.dto.PointResponse;
import com.magicpost.app.magicPost.point.entity.Point;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    PropertyMap<Address, AddressDTO> addressDTOPropertyMap() {
        return new PropertyMap<Address, AddressDTO>() {
            @Override
            protected void configure() {
                map().setZipcode(source.getZipcode());
                map().setStreet(source.getStreet());
                map().setCommune(source.getCommune().getName());
                map().setDistrict(source.getCommune().getDistrict().getName());
                map().setProvince(source.getCommune().getDistrict().getProvince().getName());
            }
        };
    }


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper
                .addMappings(addressDTOPropertyMap());
        return modelMapper;
    }
}
