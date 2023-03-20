package com.nttdata.btc.customer.app.util.mappers;

import com.nttdata.btc.customer.app.model.response.OpResponse;
import com.nttdata.btc.customer.app.proxy.beans.operation.OperationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OpResponseMapper {

    @Mapping(source = "categoryDescription", target = "categoryDescription")
    @Mapping(source = "typeDescription", target = "typeDescription")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "source_account", target = "source_account")
    @Mapping(source = "target_account", target = "target_account")
    @Mapping(source = "currency", target = "currency")
    @Mapping(source = "amount", target = "amount")
    OpResponse toOpResponse(OperationResponse entity);
}