package com.nttdata.btc.customer.app.model.response;

import lombok.*;

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
    private String productCode;
    private Double productBalance;
}