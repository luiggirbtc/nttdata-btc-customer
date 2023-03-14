package com.nttdata.btc.customer.app.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Class UpdateCustomerRequest.
 *
 * @author lrs
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateCustomerRequest extends BaseRequest {
    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    private String id_customer;
}