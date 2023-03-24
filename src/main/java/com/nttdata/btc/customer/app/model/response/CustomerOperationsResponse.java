package com.nttdata.btc.customer.app.model.response;

import com.nttdata.btc.customer.app.proxy.beans.operation.OperationResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Class response CustomerOperationsResponse.
 *
 * @author lrs
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class CustomerOperationsResponse {
    CustomerResponse customer;
    String codeProduct;
    List<OpResponse> operations;

    List<OperationResponse> ejemplos;
}