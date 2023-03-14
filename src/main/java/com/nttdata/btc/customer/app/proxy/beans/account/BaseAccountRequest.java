package com.nttdata.btc.customer.app.proxy.beans.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

/**
 * Class response BaseAccountRequest.
 *
 * @author lrs
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseAccountRequest {
    private String code_account;
    private List<String> holder_account;
    private List<String> authorized_signer;
    private String product;
    private Double balance;
    private List<String> transactions;
}