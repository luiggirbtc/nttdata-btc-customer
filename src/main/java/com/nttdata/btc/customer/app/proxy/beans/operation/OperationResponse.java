package com.nttdata.btc.customer.app.proxy.beans.operation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Class response OperationResponse.
 *
 * @author lrs
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class OperationResponse {
    @Schema(description = "Balance account", example = "12350.0")
    private String id_operation;

    @Schema(description = "Register date", example = "2023-03-11T21:58:49.101+00:00")
    private Date register_date;

    @Schema(description = "Status operation", example = "true")
    private Boolean status = false;

    @Schema(description = "Category operation", example = "1 - Transacción | 2 - Transferencia | 3 - Movimiento/Consumo de tarjeta")
    private Byte category;

    @Schema(description = "Type operation", example = "1 = Depósito | 2= Retiro | 3 = Pago crédito")
    private Byte type;

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

    @Schema(description = "Type description", example = "Depósito")
    private String typeDescription;

    @Schema(description = "Category description", example = "Pago crédito")
    private String categoryDescription;
}