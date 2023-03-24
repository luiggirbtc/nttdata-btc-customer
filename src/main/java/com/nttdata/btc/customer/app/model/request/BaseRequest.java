package com.nttdata.btc.customer.app.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseRequest {

    /**
     * 1 = personal, 2 = empresarial
     */
    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    @Schema(required = true, description = "Type customer", example = "1 = Personal | 2 = Empresarial")
    private String type_customer;

    /**
     * 0 = ninguno, 1 = titular , 2 = firmante
     */
    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    @Schema(required = true, description = "Category customer", example = "0 = ninguno | 1 = titular | 2 = firmante")
    private String category_customer;

    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    @Schema(required = true, description = "Name customer", example = "Pedro")
    private String name;

    @Schema(required = false, description = "Surname customer", example = "Arenas")
    private String surname;

    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    @Schema(required = true, description = "Type document", example = "1 = DNI | 2 = RUC | 3 = CE")
    private String type_document;

    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    @Schema(required = true, description = "Number document", example = "12345678")
    private String number_document;

    @Schema(required = false, description = "Phone number", example = "123456789")
    private String phone;

    @Schema(required = false, description = "Email", example = "pedro@gmail.com")
    @Email(regexp = ".+[@].+[\\.].+")
    private String email;
}