package com.gymmanagement.backend.mappers;

import com.gymmanagement.backend.dto.get.PaymentGetDTO;
import com.gymmanagement.backend.dto.post.PaymentPostDTO;
import com.gymmanagement.backend.dto.put.PaymentPutDTO;
import com.gymmanagement.backend.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "paymentDate", ignore = true)
    @Named("mapPostDtoToPaymentEntity")
    Payment mapPostDtoToPaymentEntity(PaymentPostDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "paymentDate", ignore = true)
    @Named("updatePaymentEntityFromPutDto")
    void updatePaymentEntityFromPutDto(PaymentPutDTO dto, @MappingTarget Payment entity);

    @Mapping(target = "customer", source = "customer", qualifiedByName = "mapCustomerEntityToGetDto")
    @Named("mapPaymentEntityToGetDto")
    PaymentGetDTO mapPaymentEntityToGetDto(Payment entity);
}
