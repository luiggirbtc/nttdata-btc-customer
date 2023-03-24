package com.nttdata.btc.customer.app.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
public class CustomerResponse {
    @Schema(description = "Type customer", example = "1 = Personal | 2 = Empresarial")
    private String type_customer;

    @Schema(description = "Category customer", example = "0 = ninguno | 1 = titular | 2 = firmante")
    private String category_customer;

    @Schema(description = "Name customer", example = "Pedro")
    private String name;

    @Schema(description = "Surname customer", example = "Arenas")
    private String surname;

    @Schema(description = "Type document", example = "1 = DNI | 2 = RUC | 3 = CE")
    private String type_document;

    @Schema(description = "Number document", example = "12345678")
    private String number_document;

    @Schema(description = "Phone number", example = "123456789")
    private String phone;

    @Schema(description = "Email", example = "pedro@gmail.com")
    private String email;

    @Schema(description = "Id customer", example = "120cf999662f294fc1234567")
    private String id_customer;

    @Schema(description = "Register date", example = "2023-03-11T21:58:49.101+00:00")
    private Date registerDate;

    @Schema(description = "Status customer", example = "true")
    private Boolean status = false;
}