package com.nttdata.btc.customer.app.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class response BalanceProductResponse.
 *
 * @author lrs
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class BalanceProductResponse {
    @Schema(description = "Product code", example = "640c24cd3b905b25cfa2f25a")
    private String productCode;

    @Schema(description = "Balance product", example = "20000.0")
    private Double productBalance;
}