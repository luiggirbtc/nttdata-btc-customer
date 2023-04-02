package com.nttdata.btc.customer.app.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Class RedisCustomer
 *
 * @author lrs
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RedisCustomer implements Serializable {
    private String id_customer;
    private String type_customer;
    private String category_customer;
    private String name;
    private String surname;
    private String type_document;
    private String number_document;
    private String phone;
    private String email;
    private Date registerDate;
    private Boolean status = false;
}