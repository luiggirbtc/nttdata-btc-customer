package com.nttdata.btc.customer.app.model.response;

import com.nttdata.btc.customer.app.proxy.beans.operation.OperationResponse;
import lombok.*;

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