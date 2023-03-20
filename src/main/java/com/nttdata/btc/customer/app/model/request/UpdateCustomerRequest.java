package com.nttdata.btc.customer.app.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateCustomerRequest extends BaseRequest {
    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    @Schema(required = true, description = "Id customer", example = "120cf999662f294fc1234567")
    private String id_customer;
}