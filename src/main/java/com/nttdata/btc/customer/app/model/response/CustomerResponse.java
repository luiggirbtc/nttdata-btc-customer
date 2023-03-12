package com.nttdata.btc.customer.app.model.response;

import com.nttdata.btc.customer.app.model.request.BaseRequest;
import lombok.*;

import java.util.Date;

/**
 * Response bean CustomerResponse.
 *
 * @author lrs
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class CustomerResponse extends BaseRequest {
    private String id_customer;
    private Date registerDate;
    private Boolean status = false;
}
