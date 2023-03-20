package com.nttdata.btc.customer.app.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Class response AccountBalanceResponse.
 *
 * @author lrs
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AccountBalanceResponse {
    @Schema(description = "Code account", example = "193-1853946-0-26")
    private String code_account;

    @Schema(description = "Product code", example = "640c24cd3b905b25cfa2f25a")
    private String product;

    @Schema(description = "Balance account", example = "12350.0")
    private Double balance;
}