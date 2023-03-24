package com.nttdata.btc.customer.app.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * Entity BaseRequest.
 *
 * @author lrs
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceRequest {
    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    @Schema(required = true, description = "Type document", example = "1 = DNI | 2 = RUC | 3 = CE")
    private String typeDocument;

    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    @Schema(required = true, description = "Number document", example = "12345678")
    private String numberDocument;

    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    @Schema(required = true, description = "Code product", example = "640c24cd3b905b25cfa2f25a")
    private String codeProduct;
}