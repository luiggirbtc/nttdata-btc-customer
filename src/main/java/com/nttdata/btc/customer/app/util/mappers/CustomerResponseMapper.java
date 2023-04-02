package com.nttdata.btc.customer.app.util.mappers;

import com.nttdata.btc.customer.app.cache.RedisCustomer;
import com.nttdata.btc.customer.app.model.response.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CustomerResponseMapper {
    @Mapping(target = "id_customer", source = "id_customer")
    @Mapping(target = "type_customer", source = "type_customer")
    @Mapping(target = "category_customer", source = "category_customer")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "type_document", source = "type_document")
    @Mapping(target = "number_document", source = "number_document")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "registerDate", source = "registerDate")
    @Mapping(target = "status", source = "status")
    CustomerResponse redisToResponse(RedisCustomer response);

    @Mapping(target = "id_customer", source = "id_customer")
    @Mapping(target = "type_customer", source = "type_customer")
    @Mapping(target = "category_customer", source = "category_customer")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "type_document", source = "type_document")
    @Mapping(target = "number_document", source = "number_document")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "registerDate", source = "registerDate")
    @Mapping(target = "status", source = "status")
    RedisCustomer toRedis(CustomerResponse response);
}