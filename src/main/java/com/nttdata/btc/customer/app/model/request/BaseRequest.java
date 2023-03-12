package com.nttdata.btc.customer.app.model.request;

import lombok.*;

/**
 * Entity BaseRequest.
 *
 * @author lrs
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BaseRequest {

    /**
     * 1 = personal, 2 = empresarial
     */
    private String type_customer;

    /**
     * 0 = ninguno, 1 = titular , 2 = firmante
     */
    private Byte category_customer;

    private String name;

    private String surname;

    private String type_document;

    private String number_document;

    private String phone;

    private String email;
}
