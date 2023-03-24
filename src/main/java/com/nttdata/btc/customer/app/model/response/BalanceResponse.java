package com.nttdata.btc.customer.app.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Class response BalanceResponse.
 *
 * @author lrs
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class BalanceResponse {
    CustomerResponse customer;
    List<AccountBalanceResponse> accounts;
    List<BalanceProductResponse> balanceProducts;
}