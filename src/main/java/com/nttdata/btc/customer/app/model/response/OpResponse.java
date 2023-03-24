package com.nttdata.btc.customer.app.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Class response OpResponse.
 *
 * @author lrs
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OpResponse {
    @Schema(description = "Category description", example = "Pago crédito")
    private String categoryDescription;

    @Schema(description = "Type description", example = "Depósito")
    private String typeDescription;

    @Schema(description = "Description operation", example = "Pago factura.")
    private String description;

    @Schema(description = "Code source account", example = "640cc29c60650d1637e040a90")
    private String source_account;

    @Schema(description = "Code target account", example = "123-1234567-0-00")
    private String target_account;

    @Schema(description = "Currency operation", example = "PEN | USD")
    private String currency;

    @Schema(description = "Amount operation", example = "250.0")
    private BigDecimal amount;
}