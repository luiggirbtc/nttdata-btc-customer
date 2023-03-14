package com.nttdata.btc.customer.app.proxy.beans.account;

import java.util.Date;
import java.util.List;

import lombok.*;

/**
 * Class response ProductResponse.
 *
 * @author lrs
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AccountResponse {//extends BaseAccountRequest {
    private String id_account;
    private Date register_date;
    private Boolean status = false;

    private String code_account;
    private List<String> holder_account;
    private List<String> authorized_signer;
    private String product;
    private Double balance;
    private List<String> transactions;
}