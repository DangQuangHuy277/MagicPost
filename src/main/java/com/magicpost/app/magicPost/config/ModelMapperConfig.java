package com.magicpost.app.magicPost.config;

import com.magicpost.app.magicPost.actor.entity.Customer;
import com.magicpost.app.magicPost.actor.dto.CustomerDTO;
import com.magicpost.app.magicPost.address.dto.AddressDTO;
import com.magicpost.app.magicPost.address.entity.Address;
import com.magicpost.app.magicPost.user.dto.UserResponse;
import com.magicpost.app.magicPost.user.entity.User;
import com.magicpost.app.magicPost.user.entity.leader.CompanyLeader;
import com.magicpost.app.magicPost.user.entity.leader.GatheringLeader;
import com.magicpost.app.magicPost.user.entity.leader.TransactionLeader;
import com.magicpost.app.magicPost.user.entity.staff.GatheringStaff;
import com.magicpost.app.magicPost.user.entity.staff.TransactionStaff;
import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    <T extends User> Converter<T, UserResponse> userResponseConverter() {
        return new Converter<T, UserResponse>() {
            @Override
            public UserResponse convert(MappingContext<T, UserResponse> mappingContext) {
                User source = mappingContext.getSource();
                if (source == null) return null;
                UserResponse userResponse = new UserResponse();
                userResponse.setId(source.getId());
                userResponse.setUsername(source.getUsername());
                userResponse.setEmail(source.getEmail());
                userResponse.setPhone(source.getPhone());
                userResponse.setRole(source.getClass().getSimpleName());
                switch (source){
                    case TransactionLeader tl -> userResponse.setPointId(tl.getTransactionPoint().getId());
                    case GatheringLeader gl -> userResponse.setPointId(gl.getGatheringPoint().getId());
                    case GatheringStaff gs -> userResponse.setPointId(gs.getGatheringPoint().getId());
                    case TransactionStaff ts -> userResponse.setPointId(ts.getTransactionPoint().getId());
                    default -> {}
                }
                return userResponse;
            }
        };
    }

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


    PropertyMap<Customer, CustomerDTO> customerDTOPropertyMap(){
        return new PropertyMap<Customer, CustomerDTO>(){
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setName(source.getName());
                map().setPhone(source.getPhone());
                map().getAddress().setStreet(source.getAddress().getStreet());
                map().getAddress().setZipcode(source.getAddress().getZipcode());
                map().getAddress().setCommune(source.getAddress().getCommune().getName());
                map().getAddress().setDistrict(source.getAddress().getCommune().getDistrict().getName());
                map().getAddress().setProvince(source.getAddress().getCommune().getDistrict().getProvince().getName());
            }
        };
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.addConverter(userResponseConverter());
        modelMapper.addMappings(addressDTOPropertyMap());
        modelMapper.addMappings(customerDTOPropertyMap());
        modelMapper.createTypeMap(CompanyLeader.class, UserResponse.class).setConverter(userResponseConverter());
        modelMapper.createTypeMap(GatheringLeader.class, UserResponse.class).setConverter(userResponseConverter());
        modelMapper.createTypeMap(TransactionLeader.class, UserResponse.class).setConverter(userResponseConverter());
        modelMapper.createTypeMap(GatheringStaff.class, UserResponse.class).setConverter(userResponseConverter());
        modelMapper.createTypeMap(TransactionStaff.class, UserResponse.class).setConverter(userResponseConverter());
        return modelMapper;
    }
}
