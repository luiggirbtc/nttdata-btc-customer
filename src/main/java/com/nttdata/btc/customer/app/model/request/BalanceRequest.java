package com.nttdata.btc.customer.app.model.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * Entity BaseRequest.
 *
 * @author lrs
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BalanceRequest {
    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    private String typeDocument;

    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    private String numberDocument;

    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    private String codeProduct;
}