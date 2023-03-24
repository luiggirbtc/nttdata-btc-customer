package com.nttdata.btc.customer.app.proxy.beans.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity BaseProductRequest.
 *
 * @author lrs
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseProductRequest {
    private String name;
    private String type;
    private String type_description;
    private String category;
    private String category_description;
    private String description;
    private Double statement_fee;
    private Double statement_transaction;
    private Integer max_operations;
}