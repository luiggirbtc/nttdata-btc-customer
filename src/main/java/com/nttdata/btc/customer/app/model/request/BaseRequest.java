package com.nttdata.btc.customer.app.model.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    private String type_customer;

    /**
     * 0 = ninguno, 1 = titular , 2 = firmante
     */
    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    private String category_customer;

    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    private String name;

    private String surname;

    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    private String type_document;

    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    private String number_document;

    private String phone;

    @Email(regexp = ".+[@].+[\\.].+")
    private String email;
}