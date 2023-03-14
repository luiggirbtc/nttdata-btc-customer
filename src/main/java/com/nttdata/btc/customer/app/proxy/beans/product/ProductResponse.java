package com.nttdata.btc.customer.app.proxy.beans.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

/**
 * Response bean ProductResponse.
 *
 * @author lrs
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponse extends BaseProductRequest {
    private String id_product;
    private Date registerDate;
    private Boolean status = false;
}