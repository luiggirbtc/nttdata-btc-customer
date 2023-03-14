package com.nttdata.btc.customer.app.model.response;

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
    private String code_account;
    private String product;
    private Double balance;
}